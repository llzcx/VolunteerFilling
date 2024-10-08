package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 陈翔
 */
@Data
public class SysRoleApi {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long apiId;
    private Integer roleId;

    public SysRoleApi(Long apiId, Integer roleId) {
        this.apiId = apiId;
        this.roleId = roleId;
    }
}