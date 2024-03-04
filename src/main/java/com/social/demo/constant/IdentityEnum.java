package com.social.demo.constant;

import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;

public enum IdentityEnum {

    STUDENT(0, "学生", "查看个人信息\n" +
            "上传/修改证件照\n" +
            "修改密码，修改电话\n" +
            "修改收件地址(收件人，收件电话，详细地址)\n" +
            "查看志愿，填报/修改志愿\n" +
            "个人月综测查看，个人学期综测查看，签字确认综测\n" +
            "问题申诉，申诉历史查看，申诉进度查看\n" +
            "班级综合测评查看，导出\n" +
            "查询排名\n", false),

    TEACHER(1, "老师", "个人信息查看\n" +
            "修改电话\n" +
            "查看班级志愿情况\n", true),

    APPRAISAL_TEAM(2, "综测成员", "查看基本资料\n" +
            "修改密码\n" +
            "查看，修改，查询成员班级综测\n" +
            "|电子签名提交综测\n" +
            "申诉情况查看，处理\n", false),

    CLASS_ADVISER(3, "班主任", "个人信息查看\n" +
            "修改电话\n" +
            "查看班级志愿情况\n" +
            "查看综合测评情况\n" +
            "查看班级成员列表\n" +
            "(批量)重置学生密码\n" +
            "设置测评小组成员/撤销职位\n" +
            "分配小组成员负责学生\n" +
            "申诉处理\n" +
            "查看/修改学生详细信息\n", true),
    SUPER(4, "超级管理员", "待补充", false),
    ;

    private Integer roleId;
    private String str;

    private String des;

    //身份一旦初始化了是否可以改变
    private Boolean variable;

    IdentityEnum(Integer roleId, String message, String des, Boolean variable) {
        this.roleId = roleId;
        this.str = message;
        this.des = des;
        this.variable = variable;
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

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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
     *
     * @param
     * @return
     */
    public static IdentityEnum searchByCode(Integer roleId) {
        if (roleId == null) {
            throw new SystemException(ResultCode.PARAM_IS_BLANK);
        }
        for (IdentityEnum value : values()) {
            if (value.roleId.equals(roleId)) {
                return value;
            }
        }
        throw new SystemException(ResultCode.ROLE_NOT_EXISTS);
    }


    /**
     * 利用字符串搜索身份
     * 1.可以用于查看某个string对应的身份是否存在
     * 2. string 到 code 的转化
     *
     * @param msg
     * @return
     */
    public static IdentityEnum searchByString(String msg) {
        if (msg == null) {
            throw new SystemException(ResultCode.PARAM_IS_BLANK);
        }
        for (IdentityEnum value : values()) {
            if (value.str.equals(msg)) {
                return value;
            }
        }
        throw new SystemException(ResultCode.ROLE_NOT_EXISTS);
    }

    public Boolean isVariable() {
        return variable;
    }

}
