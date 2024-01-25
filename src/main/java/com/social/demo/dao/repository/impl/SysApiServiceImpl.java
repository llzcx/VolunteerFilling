package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.dao.mapper.SysRoleApiMapper;
import com.social.demo.dao.repository.ISysApiService;
import com.social.demo.entity.SysApi;
import com.social.demo.entity.SysRoleApi;
import com.social.demo.entity.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 *
 * @author 陈翔
 */
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements ISysApiService {

    @Autowired
    SysApiMapper apiMapper;

    @Autowired
    SysRoleApiMapper roleApiMapper;

    @Override
    @Transactional
    public Boolean addApis(Set<Long> apis, Integer roleCode) {
        IdentityEnum role = IdentityEnum.searchByCode(roleCode);
        for (Long api : apis) {
            SysApi sysApi = apiMapper.selectById(api);
            if(sysApi == null){
                throw new SystemException(ResultCode.PARAMETER_RESOURCE_DOES_NOT_EXIST);
            }
            roleApiMapper.insert(new SysRoleApi(api, role.getRoleId()));
        }
        return true;
    }

    @Override
    public List<SysApi> selectApisWithRoles() {
        List<SysApi> sysApis = apiMapper.selectApisWithRoles();
        for (SysApi sysApi : sysApis) {
            for (SysRoleVo role : sysApi.getRoles()) {
                role.setRoleName(IdentityEnum.searchByCode(role.getRoleId()).getMessage());
            }
        }
        return sysApis;
    }
}