package com.social.demo.entity;

import lombok.Data;

@Data
public class WishResult {
    /**
     * 班级
     */
    private String className;
    /**
     * 科目
     */
    private String subjects;
    /**
     * 学校名字
     */
    private String name;
    /**
     * 省份
     */
    private String province;
    /**
     * 学生名字
     */
    private String userName;
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 性别
     */
    private String sex;
    /**
     * 成绩
     */
    private Integer score;
    /**
     * 第一志愿
     */
    private String firstName;
    /**
     * 第二志愿
     */
    private String secondName;
    /**
     * 第三志愿
     */
    private String thirdName;
    /**
     * 录取结果名字
     */
    private String majorName;
    /**
     * 标注
     */
    private Integer mateWay;
    /**
     * 家长电话
     */
    private String parentPhone;
    /**
     * 电话
     */
    private String phone;
}
