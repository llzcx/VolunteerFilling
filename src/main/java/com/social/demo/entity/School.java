package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学校信息
 *
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    /**
     * 院校编码
     */
    private Long number;
    /**
     * 学校名额
     */
    private String name;
}
