package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生信息
 *
 * @author 杨世博
 * @date 2023/12/12 8:37
 * @description Student
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private Long userId;
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 学校编号
     */
    private Long schoolNumber;
    /**
     * 来源省份
     */
    private String province;
    /**
     * 性质计划
     */
    private String plan;
    /**
     * 父母电话
     */
    private String parentPhone;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 入学年份
     */
    private Integer enrollmentYear;
    /**
     * 组合id
     */
    private Long groupId;
    /**
     * 身份证号码
     */
    private String idCard;
}
