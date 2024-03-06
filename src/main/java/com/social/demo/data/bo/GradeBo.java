package com.social.demo.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/25 15:12
 * @description GradeBo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeBo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 科目成绩
     */
    private String grade;
    /**
     * 总成绩
     */
    private Double score;
}
