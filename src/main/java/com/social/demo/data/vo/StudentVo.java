package com.social.demo.data.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.social.demo.data.bo.ConsigneeBo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author 杨世博
 */
@Data
public class StudentVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    private String userNumber;
    /**
     * 民族
     */
    private String nation;
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
     * 计划性质
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
     * 选课Hashcode
     */
    private Integer hashcode;
    /**
     * 选课
     */
    private List<String> subjects;
    /**
     * 性别
     */
    private String sex;

    /**
     * 收获信息
     */
    private ConsigneeBo consignee;
    /**
     * 证件照
     */
    private String headshot;
}
