package com.social.demo.data.dto;

import lombok.Data;

@Data
public class ResultDto {
    /**
     * 学生账号
     */
    private  String userNumber;
    /**
     * 录取结果名字
     */
    private String admissionResultName;
}
