package com.social.demo.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 签名
 */
@Data
public class Autograph {
    /**
     * 签名id
     */
    private Long autographId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 图片地址
     */
    private String signature;
    /**
     * 填报次数
     */
    private Integer frequency;
    /**
     * 第一志愿名字
     */
    private String firstName;
    /**
     * 第二志愿名字
     */
    private String secondName;
    /**
     * 第三志愿名字
     */
    private String thirdName;
    /**
     * 志愿时间id
     */
    private Long timeId;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
