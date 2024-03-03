package com.social.demo.manager.security.identity;

import cn.hutool.core.text.AntPathMatcher;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.dao.repository.ISysApiService;
import com.social.demo.entity.SysApi;
import com.social.demo.entity.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 动态RBAC
 */
@Slf4j
public class DynamicIdentityImpl implements IdentityAuthentication {

    @Autowired
    ISysApiService apiService;

    @Autowired
    AntPathMatcher antPathMatcher;

    @Override
    public boolean check(String requestURL, IdentityEnum identity, Method method) {
        List<SysApi> sysApis = apiService.selectApisWithRoles();
        for (SysApi sysApi : sysApis) {
            if (antPathMatcher.match(sysApi.getPattern(), requestURL)) {
                log.info("匹配的接口是：{}",sysApi);
                List<SysRoleVo> roles = sysApi.getRoles();
                //第二步：这个接口对应的身份要求必须满足
                for (SysRoleVo role : roles) {
                    if (role.getRoleId().equals(identity.getRoleId())) {
                        return true;
                    }
                }
                throw new SystemException(ResultCode.UNAUTHORIZED_ACCESS);
            }
        }
        //到这里就说明数据库当中没有Pattern可以匹配当前RequestURL，直接拒绝访问
        throw new SystemException(ResultCode.ACCESS_WAS_DENIED);
    }
}
