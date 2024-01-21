package com.social.demo.common;

/**
 * @Author: 陈翔
 * 规定:
 * #200表示成功
 * #999表示默认失败
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),

    ERROR_UNKNOWN(400,"未知错误"),

    ERROR_404(404,"网页或文件未找到"),

    ERROR_505(500,"出错了"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),


    /* 用户错误 */
    PASSWORD_ERROR(2001,"密码错误"),
    CODE_ERROR(2002,"验证码错误"),
    NOT_LOGIN(2003,"未登录"),
    TOKEN_TIME_OUT(2004,"token已过期"),
    TOKEN_EXCEPTION(2005,"token异常"),
    AUTHENTICATION_EXCEPTION(2006,"身份验证异常"),
    ACCESS_WAS_DENIED(2007,"访问被拒绝"),
    OPERATION_FAIL(2008,"操作失败"),
    UNAUTHORIZED_ACCESS(2009,"无权访问"),
    USERNAME_HAS_ALREADY_BEEN_USED(2010,"用户名已经被使用"),
    CODE_TIME_OUT(2011,"验证码过期"),
    USER_NOT_AUTHENTICATED(2012,"用户未认证"),
    ROLE_NOT_EXISTS(2013,"角色名不存在"),
    SCHOOL_ALREADY_EXISTS(2014,"学校编码或学校名已存在"),
    SCHOOL_NOT_EXISTS(2015,"学校不存在"),
    USER_NOT_EXISTS(2016, "用户不存在"),
    USER_IS_CLASS_TEACHER(2017, "该老师已经是班主任了"),
    IS_EXISTS(2018, "已存在"),
    USER_IS_EXISTS(2019, "用户已存在"),
    USER_HAVE_CLASS(2020, "该老师是班主任，请先将班主任职务去除"),
    APPRAISAL_NOT_EXISTS(2021, "该综测记录不存在"),
    HEADSHOT_NOT_EXISTS(2022, "证件照不存在"),
    USER_NOT_MATCH_DATA(2023, "用户与数据不一致"),
    CLASS_NOT_MATCH_DATA(2024, "班级与数据不一致"),
    SUBJECT_HAVE_CLASS(2025,"科目已存在"),



        /*接口异常*/
    UNDEFINED(3001,"枚举范围错误"),
    NULL_POINT_EXCEPTION(3002,"空指针异常"),
    ENCODING_ANOMALY(3003,"系统编码错误"),
    DATABASE_DATA_EXCEPTION(3004,"数据库数据异常"),
    UPLOAD_ERROR(3005,"上传失败");


    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
}
