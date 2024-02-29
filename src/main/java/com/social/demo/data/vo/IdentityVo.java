package com.social.demo.data.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdentityVo implements Serializable {
    private Integer roleId;
    private String strName;
    private String description;

    public IdentityVo(Integer roleId, String strName, String description) {
        this.roleId = roleId;
        this.strName = strName;
        this.description = description;
    }
}
