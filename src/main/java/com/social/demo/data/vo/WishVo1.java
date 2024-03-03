package com.social.demo.data.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WishVo1 {

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
}
