package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.SysRole;

import java.util.List;

/**
 * @author 陈翔
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 通过用户id获取身份列表
     *
     * @param userId@return
     */
    List<SysRole> getRoleByUserId(Long userId);


    /**
     * 删除身份
     * @param roleName
     * @return
     */
    Boolean deleteRole(String roleName);
}
