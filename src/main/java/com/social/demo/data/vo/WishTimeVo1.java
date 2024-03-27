package com.social.demo.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.social.demo.entity.Autograph;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WishTimeVo1 {
    /**
     * 志愿时间段id
     */
    private Long Id;
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
    private Long state;
    /**
     * 历史记录
     */
    private List<Autograph> autographList;
}
