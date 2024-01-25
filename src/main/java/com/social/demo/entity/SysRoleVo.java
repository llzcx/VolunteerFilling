package com.social.demo.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author 陈翔
 */
@Data
@ToString
public class SysRoleVo {
    private Integer roleId;

    private String roleName;

    public SysRoleVo(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}