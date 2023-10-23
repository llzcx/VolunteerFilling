package com.social.demo.manager.security.authorize;

import cn.hutool.core.text.AntPathMatcher;
import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.entity.SysApi;
import com.social.demo.entity.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 用于获取该路径需要的权限
 * @author 陈翔
 */
@Slf4j
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    SysApiMapper sysApiMapper;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
                                               throws IllegalArgumentException {
        String requestURI = 
                   ((FilterInvocation) object).getRequest().getRequestURI();
        List<SysApi> allMenu = sysApiMapper.selectApisWithRoles();
        for (SysApi menu : allMenu) {
            if (antPathMatcher.match(menu.getPattern(), requestURI)) {
                String[] roles = menu.getRoles().stream()
                               .map(SysRole::getRoleName).toArray(String[]::new);
                log.info("接口路径匹配成功，接口要求的身份：{}", Arrays.toString(roles));
                return SecurityConfig.createList(roles);
            }
        }
        log.info("接口路径匹配失败");
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}