package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级信息
 * @author 杨世博
 * @date 2023/12/4 15:48
 * @description ClassVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassModifyDto {
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 工号
     */
    private String userNumber;
    /**
     * 班级名
     */
    private String className;
}
