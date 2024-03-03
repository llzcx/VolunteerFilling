package com.social.demo.constant;

/**
 *
 */
public enum StateEnum {
    NOT_FILL(0, "未填报"),
    FILLING(1, "填报中"),
    TO_FILL(2, "已填报")
    ;

    private Integer state;

    private String mag;

    StateEnum(Integer state, String mag) {
        this.state = state;
        this.mag = mag;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }
}
