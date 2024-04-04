package com.social.demo.data.vo;

import lombok.Data;

@Data
public class NotAcceptedVo{
    /**
     * 学号
     */
    private Long userNumber;
    /**
     * 学生名字
     */
    private String username;
    /**
     * 学生性别
     */
    private String sex;
    /**
     * 班级
     */
    private String className;
    /**
     * 目标学校
     */
    private String schoolName;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 父母联系方式
     */
    private String parentPhone;
    /**
     * 地址
     */
    private String address;
}
