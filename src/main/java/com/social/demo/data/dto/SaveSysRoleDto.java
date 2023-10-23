package com.social.demo.data.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈翔
 */
@Data
@EqualsAndHashCode
public class SaveSysRoleDto {
    /**
     * 名字
     */
    private String roleName;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Boolean status;
}
