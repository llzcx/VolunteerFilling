package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/21 20:56
 * @description AppraisalContentDto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppraisalContentVo {
    /**
     * 学生学号，需要初始化
     */
    @Getter
    private String userNumber;
    /**
     * 学生姓名，需要初始化
     */
    @Getter
    private String username;
    @Getter
    private String add1;
    @Getter
    private String sub1;
    private Double point1;
    @Getter
    private String add2;
    @Getter
    private String sub2;
    private Double point2;
    @Getter
    private String add3;
    @Getter
    private String sub3;
    private Double point3;
    @Getter
    private String add4;
    @Getter
    private String sub4;
    private Double point4;
    @Getter
    private String add5;
    @Getter
    private String sub5;
    private Double point5;
    private Double add_total;
    private Double sub_total;
    /**
     * 上个月的分数需要初始化
     */
    @Getter
    private Double pre_total;
    private Double point_total;

    public AppraisalContentVo(String userNumber, String username, Double pre_total) {
        this.userNumber = userNumber;
        this.username = username;
        this.pre_total = pre_total;
        this.add1 = "";
        this.sub1 = "";
        this.add2 = "";
        this.sub2 = "";
        this.add3 = "";
        this.sub3 = "";
        this.add4 = "";
        this.sub4 = "";
        this.add5 = "";
        this.sub5 = "";
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getPoint_total() {
        return point_total != null ? point_total : 0;
    }
}
