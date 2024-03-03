package com.social.demo.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WishVo {
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
     * 第一志愿
     */
    private String firstName;
    /**
     * 第二志愿
     */
    private Long second;
    /**
     * 第二志愿
     */
    private String secondName;
    /**
     * 第三志愿
     */
    private Long third;
    /**
     * 第三志愿
     */
    private String thirdName;
    /**
     * 时间段id
     */
    private Long timeId;
    /**
     * 填报次数
     */
    private Integer frequency;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}
