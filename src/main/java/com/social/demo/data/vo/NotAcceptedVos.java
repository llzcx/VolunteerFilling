package com.social.demo.data.vo;

import lombok.Data;

import java.util.List;

@Data
public class NotAcceptedVos {
    /**
     * 班级
     */
    private String className;
    /**
     * 学生信息
     */
    private List<NotAcceptedVo> notAcceptedVoList;
}
