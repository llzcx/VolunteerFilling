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
    private Double pre_total;
    private Double point_total;

    public AppraisalContentVo(String userNumber, String username, Double pre_total) {
        this.userNumber = userNumber;
        this.username = username;
        this.pre_total = pre_total;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public Double getPoint1() {
        return point1 != null ? point1 : 0;
    }

    public void setPoint1(Double point1) {
        this.point1 = point1;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public Double getPoint2() {
        return point2 != null ? point2 : 0;
    }

    public void setPoint2(Double point2) {
        this.point2 = point2;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

    public Double getPoint3() {
        return point3 != null ? point3 : 0;
    }

    public void setPoint3(Double point3) {
        this.point3 = point3;
    }

    public void setAdd4(String add4) {
        this.add4 = add4;
    }

    public void setSub4(String sub4) {
        this.sub4 = sub4;
    }

    public Double getPoint4() {
        return point4 != null ? point4 : 0;
    }

    public void setPoint4(Double point4) {
        this.point4 = point4;
    }

    public void setAdd5(String add5) {
        this.add5 = add5;
    }

    public void setSub5(String sub5) {
        this.sub5 = sub5;
    }

    public Double getPoint5() {
        return point5 != null ? point5 : 0;
    }

    public void setPoint5(Double point5) {
        this.point5 = point5;
    }

    public Double getAdd_total() {
        return add_total != null ? add_total : 0;
    }

    public void setAdd_total(Double add_total) {
        this.add_total = add_total;
    }

    public Double getSub_total() {
        return sub_total != null ? sub_total : 0;
    }

    public void setSub_total(Double sub_total) {
        this.sub_total = sub_total;
    }

    public Double getPre_total() {
        return pre_total != null ? pre_total : 0;
    }

    public void setPre_total(Double pre_total) {
        this.pre_total = pre_total;
    }

    public Double getPoint_total() {
        return point_total != null ? point_total : 0;
    }

    public void setPoint_total(Double point_total) {
        this.point_total = point_total;
    }
}
