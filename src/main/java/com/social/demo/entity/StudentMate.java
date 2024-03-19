package com.social.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class StudentMate {
    /**
     * 学生id
     */
    Long studentId;
    /**
     * 学生分数
     */
    Double score;
    /**
     * 学生志愿列表
     */
    private List<Major> volunteerList;
    /**
     * 专业id
     */
    private Long majorId;
    /**
     * 专业名字
     */
    private String majorName;
    /**
     * 是否录取
     */
    private Integer state;
}
