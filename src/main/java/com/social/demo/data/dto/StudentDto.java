package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author 杨世博
 * @date 2023/11/28 19:47
 * @description StudentDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    private String userNumber;
    /**
     * 姓名
     */
    private String username;
    /**
     * 性别
     */
    private String sex;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 政治面貌
     */
    private String politicsStatus;
    /**
     * 民族
     */
    private String nation;
    /**
     * 班级
     */
    private String className;
    /**
     * 目标学校
     */
    private String school;
    /**
     * 来源省份
     */
    private String province;
    /**
     * 选考科目
     */
    private String[] subjects;
    /**
     * 计划性质
     */
    private String plan;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 父母电话
     */
    private String parentPhone;
}
