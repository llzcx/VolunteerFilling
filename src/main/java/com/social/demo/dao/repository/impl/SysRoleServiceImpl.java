package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.RoleConstant;
import com.social.demo.dao.mapper.SysRoleMapper;
import com.social.demo.dao.mapper.SysUserRoleMapper;
import com.social.demo.dao.repository.ISysRoleService;
import com.social.demo.entity.SysRole;
import com.social.demo.util.MybatisPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈翔
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>  implements ISysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> getRoleByUserId(String userName) {
        return sysRoleMapper.selectRoleListByUserName(userName);
    }

    @Override
    public Boolean deleteRole(String roleName) {
        if(roleName.equals(RoleConstant.ROLE_ADMIN) || roleName.equals(RoleConstant.ROLE_USER)){
            throw new SystemException(ResultCode.OPERATION_FAIL);
        }
        sysRoleMapper.delete(MybatisPlusUtil.queryWrapperEq("role_name",roleName));
        return true;
    }
}
