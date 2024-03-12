package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/21 20:56
 * @description AppraisalContentDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalContentVo {
    private String userNumber;
    private String username;
    private String add1;
    private String sub1;
    private Double point1;
    private String add2;
    private String sub2;
    private Double point2;
    private String add3;
    private String sub3;
    private Double point3;
    private String add4;
    private String sub4;
    private Double point4;
    private String add5;
    private String sub5;
    private Double point5;
    private Double add_total;
    private Double sub_total;
    private Double pre_total;
    private Double point_total;
}
