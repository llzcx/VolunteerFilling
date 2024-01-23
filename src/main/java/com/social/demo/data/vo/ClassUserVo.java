package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2023/12/21 21:59
 * @description ClassUserVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassUserVo {
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 姓名
     */
    private String username;
    /**
     * 职位
     * 1 - 学生
     * 2 - 综测小组成员
     */
    private Integer identity;
    /**
     * 该月综测分
     */
    private Integer appraisalScore;
}
