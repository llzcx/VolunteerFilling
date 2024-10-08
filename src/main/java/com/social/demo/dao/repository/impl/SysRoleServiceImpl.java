package com.social.demo.dao.repository.impl;

import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.SysRoleApiMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 杨世博
 * @date 2024/3/2 15:59
 * @description SysRoleServiceImpl
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SysRoleApiMapper sysRoleApiMapper;
    @Override
    public void saveUserRole(Long userId, Integer roleId) {
        if(roleId.equals(IdentityEnum.SUPER.getRoleId())){
            throw new SystemException(ResultCode.SUPER_PERMISSION_CANT_CHANGE);
        }
        IdentityEnum identityEnum = IdentityEnum.searchByCode(roleId);
        if(!identityEnum.isVariable()){
            throw new SystemException(ResultCode.AUTH_CHANGE);
        }
        userMapper.updateIdentityByUserId(userId,identityEnum.getRoleId());
    }
}
