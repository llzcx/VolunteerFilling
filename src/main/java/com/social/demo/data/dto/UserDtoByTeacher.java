package com.social.demo.data.dto;

import com.social.demo.entity.SubjectGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoByTeacher {
    /**
     * 学号
     */
    private Long userNumber;
    /**
     * 民族
     */
    private Boolean nation;
    /**
     * 班级名
     */
    private String className;
    /**
     * 目标院校
     */
    private String school;
    /**
     * 来源省份
     */
    private String province;
    /**
     * 计划
     */
    private String plan;
    /**
     * 父母电话
     */
    private String parentPhone;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 政治面貌
     */
    private String politicsStatus;
    /**
     * 姓名
     */
    private String username;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 选科id
     */
    private List<SubjectGroup> subjects;
    /**
     * 性别
     */
    private String sex;
}
