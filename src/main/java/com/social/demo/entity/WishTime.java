package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 填报类型
     */
    private Boolean type;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    /**
     * 学校编码
     */
    private Long schoolId;
    /**
     * 入学年份
     */
    private Integer ago;
}
