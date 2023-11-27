package com.social.demo.entity;

import lombok.Data;

/**
 * 班级
 *
 * @author 杨世博
 */
@Data
public class Class {
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 班主任id
     */
    private Long userId;
    /**
     * 班级名
     */
    private String className;
}
