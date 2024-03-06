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
    private Double class1;
    private Double class2;
    private Double class3;
    private Double class4;
    private Double class5;
    private Double add;
    private Double sub;
    private Double all;
}
