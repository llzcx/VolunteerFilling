package com.social.demo.entity;

import java.time.LocalDateTime;

/**
 * 志愿
 *
 * @author 杨世博
 */
public class Volunteer {
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
     * 填报类型
     */
    private Boolean type;
    /**
     * 考试填报时间
     */
    private LocalDateTime startTime;
    /**
     * 结束填报时间
     */
    private LocalDateTime endTime;
}
