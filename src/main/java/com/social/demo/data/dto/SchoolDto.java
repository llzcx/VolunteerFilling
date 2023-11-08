package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto implements Serializable {
    /**
     * 院校编码
     */
    private Long number;
    /**
     * 学校名额
     */
    private String name;
}
