package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/22 15:36
 * @description Grade
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    /**
     * 学号
     */
    private Long userId;
    /**
     * 总分
     */
    private Integer score;
    /**
     * 各科成绩
     */
    private String content;
}
