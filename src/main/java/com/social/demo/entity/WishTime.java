package com.social.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 志愿填报时间
 *
 * @author 周威宇
 */
@Data
public class WishTime {
    /**
     * 志愿时间段id
     */
    private Long id;
    /**
     * 填报类型
     */
    private Boolean type;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
    * 是否正在填报
    */
    private Boolean state;
    /**
     * 学校名称
     */
    private String school;
}
