package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.AUTO)
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
     * 录取结果id
     */
    private Long admissionResultId;
    /**
     * 录取结果名字
     */
    private String admissionResultName;
}
