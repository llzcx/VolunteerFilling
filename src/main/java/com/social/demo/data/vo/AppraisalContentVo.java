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
    private Integer month;
    private String userNumber;
    private String username;
    private String add1;
    private String sub1;
    private Integer point1;
    private String add2;
    private String sub2;
    private Integer point2;
    private String add3;
    private String sub3;
    private Integer point3;
    private String add4;
    private String sub4;
    private Integer point4;
    private String add5;
    private String sub5;
    private Integer point5;
    private Integer add_total;
    private Integer sub_total;
    private Integer pre_total;
    private Integer point_total;
}
