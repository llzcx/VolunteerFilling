package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 陈翔
 * @since 2023-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 民族
     */
    private String nation;
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 目标院校的院校编码
     */
    private Long schoolNumber;
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
     * 密码
     */
    private String password;
    /**
     * 选科id
     */
    private Long chooseId;
    /**
     * 性别
     */
    private Boolean sex;
    /**
     * 入学年份
     */
    private LocalDateTime enrollmentYear;
}
