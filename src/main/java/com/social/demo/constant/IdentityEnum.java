package com.social.demo.constant;

import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;

public enum IdentityEnum {

    STUDENT(0,"学生"),

    TEACHER(1,"老师"),

    APPRAISAL_TEAM(2,"综测成员"),

    CLASS_ADVISER(3,"班主任"),
    SUPER(4, "超级管理员"),
    ;

    private Integer roleId;
    private String str;

    IdentityEnum(Integer roleId, String message) {
        this.roleId = roleId;
        this.str = message;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMessage() {
        return str;
    }

    public void setMessage(String message) {
        this.str = message;
    }
    /**
     * 用法示例：
     *   IdentityEnum identityCode = IdentityEnum.searchByCode(1);
     *   IdentityEnum identityString = IdentityEnum.searchByString("老师");
     */

    /**
     * 利用编码搜索身份
     * 1.可以用于查看某个code对应的身份是否存在
     * 2.  到 string 的转化
     * @param
     * @return
     */
    public static IdentityEnum searchByCode(Integer roleId){
        if(roleId ==  null){
            throw new SystemException(ResultCode.PARAM_IS_BLANK);
        }
        for (IdentityEnum value : values()) {
            if(value.roleId.equals(roleId)){
                return value;
            }
        }
        throw new SystemException(ResultCode.ROLE_NOT_EXISTS);
    }


    /**
     * 利用字符串搜索身份
     * 1.可以用于查看某个string对应的身份是否存在
     * 2. string 到 code 的转化
     * @param msg
     * @return
     */
    public static IdentityEnum searchByString(String msg){
        if(msg ==  null){
            throw new SystemException(ResultCode.PARAM_IS_BLANK);
        }
        for (IdentityEnum value : values()) {
            if(value.str.equals(msg)){
                return value;
            }
        }
        throw new SystemException(ResultCode.ROLE_NOT_EXISTS);
    }
}
