package com.social.demo.demo;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author 周威宇
 */
@Data
public class Student {
    /**
     * 学生id
     */
    Long studentId;
    /**
     * 学生姓名
     */
    String studentName;
    /**
     * 学生分数
     */
    Integer score;
    /**
     * 学生志愿列表
     */
    ArrayList<School> volunteerList;
    /**
     * 录取状态
     */
    Boolean admissionStatus;
}
