package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级消息
 * @author 杨世博
 * @date 2023/12/4 15:48
 * @description ClassVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {
    /**
     * 工号
     */
    private String userNumber;
    /**
     * 班级名
     */
    private String className;
}
