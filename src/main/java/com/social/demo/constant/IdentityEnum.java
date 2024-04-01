package com.social.demo.constant;

import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;

public enum IdentityEnum {

    STUDENT(0, "学生", "学生端为学生提供全方位个人信息管理和学业管理。学生可以在该端轻松地查看和修改个人信息，包括证件照、密码、联系电话以及收件地址等。此外，学生还可以查看自己的志愿信息，并进行填报或修改。在学业方面，学生可以查看个人平时的综合测评成绩，并进行签字确认或申诉处理。同时，学生还可以查看班级综合测评成绩及排名情况，全面了解自己在学校的表现和竞争力。该端不仅提供了信息查看和管理功能，还为学生提供了参与学校管理和评价活动的渠道，助力学生更好地融入学校生活并实现个人成长", false),

    TEACHER(1, "老师", "可以设置老师为班主任\n", true),

    APPRAISAL_TEAM(2, "综测成员", "测评小组端为测评小组成员提供便捷的综合测评处理。在该端测评小组成员可以对测评账号进行密码修改。此外，他们还可以查看、编辑和查询所负责班级的综合测评情况，包括测评详细和相关加减分数据。测评小组成员具备查看和抹除学生签名的功能，以及提交学生的综合测评成绩的电子签名。在综合评价方面，他们可以查看和处理学生对综合测评成绩的申诉情况，确保评价工作的公正性和准确性。测评小组端为测评小组成员提供了一站式的学生信息管理和评价处理功能，使其能够高效地完成评价工作。", false),

    CLASS_ADVISER(3, "班主任", "班主任端为教师提供的管理工具，旨在帮助他们有效地管理学生综合测评和个人信息。该端具备多项功能，包括个人信息管理、综合测评情况查看、学生签名情况查看、班级成员列表管理、密码重置、学生申诉处理、学生详细信息修改、班级综合测评情况查看以及电子签名归档综测等。通过这些功能，教师可以更便捷地了解学生的综合表现、管理班级成员、维护数据安全，并确保评价工作的公正和准确。", true),
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
