package com.social.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 志愿
 *
 * @author 杨世博
 */
@Data
public class Wish {
    /**
     * 志愿填报id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 第一志愿
     */
    private Long first;
    /**
     * 第二志愿
     */
    private Long second;
    /**
     * 第三志愿
     */
    private Long third;
    /**
     * 时间段id
     */
    private Long timeId;
}
