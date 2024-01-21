package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 院校信息
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto implements Serializable {
    /**
     * 院校编码
     */
    private String number;
    /**
     * 学校名
     */
    private String name;
}
