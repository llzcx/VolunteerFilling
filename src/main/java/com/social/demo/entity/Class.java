package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "class_id", type = IdType.AUTO)
    private Long classId;
    /**
     * 班主任id
     */
    private Long userId;
    /**
     * 班级名
     */
    private String className;
    /**
     * 年份
     */
    private Integer year;
}
