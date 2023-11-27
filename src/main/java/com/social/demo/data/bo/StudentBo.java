package com.social.demo.data.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 杨世博
 * @date 2023/11/20 14:13
 * @description 从表格中读取到的学生数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentBo {
    /**
     * 学号
     */
    @Pattern(regexp = RegConstant.STUDENT_NUMBER)
    @ExcelProperty("学号")
    private String studentNumber;
    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String username;
    /**
     * 性别
     */
    @ExcelProperty("性别")
    private String sex;
    /**
     * 身份证号码
     */
    @Pattern(regexp = RegConstant.IDCard)
    @ExcelProperty("身份证号")
    private String idCard;
    /**
     * 政治面貌
     */
    @ExcelProperty("政治面貌")
    private String politicsStatus;
    /**
     * 民族
     */
    @ExcelProperty("民族")
    private String nation;
    /**
     * 班级
     */
    @ExcelProperty("班级")
    private String classNumber;
    /**
     * 目标院校
     */
    @ExcelProperty("目标学校")
    private String school;
    /**
     * 来源省份
     */
    @ExcelProperty("来源省份")
    private String province;
    /**
     * 选考科目
     */
    @ExcelProperty("选考科目")
    private String chooseSubject;
    /**
     * 计划性质
     */
    @ExcelProperty("计划性质")
    private String plan;
    /**
     * 家庭地址
     */
    @ExcelProperty("家庭地址")
    private String address;
    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    private String phone;
    /**
     * 父母电话
     */
    @ExcelProperty("父母电话")
    private String parentPhone;
}
