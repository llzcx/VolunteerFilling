package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/21 21:11
 * @description AppraisalTotalDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalTotalVo {
    private Integer class1;
    private Integer class2;
    private Integer class3;
    private Integer class4;
    private Integer class5;
    private Integer add;
    private Integer sup;
    private Integer all;
}
