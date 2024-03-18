package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/21 21:11
 * @description AppraisalTotalDto
 */
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

    public Double getClass1() {
        return class1 != null ? class1 : 0;
    }

    public void setClass1(Double class1) {
        this.class1 = class1;
    }

    public Double getClass2() {
        return class2 != null ? class2 : 0;
    }

    public void setClass2(Double class2) {
        this.class2 = class2;
    }

    public Double getClass3() {
        return class3 != null ? class3 : 0;
    }

    public void setClass3(Double class3) {
        this.class3 = class3;
    }

    public Double getClass4() {
        return class4 != null ? class4 : 0;
    }

    public void setClass4(Double class4) {
        this.class4 = class4;
    }

    public Double getClass5() {
        return class5 != null ? class5 : 0;
    }

    public void setClass5(Double class5) {
        this.class5 = class5;
    }

    public Double getAdd() {
        return add != null ? add : 0;
    }

    public void setAdd(Double add) {
        this.add = add;
    }

    public Double getSub() {
        return sub != null ? sub : 0;
    }

    public void setSub(Double sub) {
        this.sub = sub;
    }

    public Double getAll() {
        return all != null ? all : 0;
    }

    public void setAll(Double all) {
        this.all = all;
    }
}
