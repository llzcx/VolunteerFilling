package com.social.demo.data.dto;

import lombok.Data;

@Data
public class MateDto {
    /**
     * 学校id
     */
    private Long schoolId;
    /**
     * 匹配方式
     */
    private Integer type;
    /**
     * 志愿时间id
     */
    private Long timeId;
    /**
     * 重新匹配id
     */
    private Integer type1;
}
