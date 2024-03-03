package com.social.demo.entity;

import lombok.Data;

@Data
public class Ranking {
    /**
     * 学生id
     */
    private Long userId;
     /**
     *学生成绩
     */
     private Integer score;
    /**
     * 学生各科
     */
     private String grade;

}
