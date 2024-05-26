package com.social.demo.entity;

import lombok.Data;

@Data
public class Mate {
    /**
     * id
     */
    private Long mateId;
    /**
     * 学生id
     */
    private Long userId;
    /**
     * 专业id
     */
    private Long majorId;
    /**
     * 专业名字
     */
    private String majorName;
    /**
     * 匹配方式
     */
    private Integer mateWay;
    /**
     * 时间段Id
     */
    private Long timeId;
    /**
     * 所属学院
     */
    private String college;
}
