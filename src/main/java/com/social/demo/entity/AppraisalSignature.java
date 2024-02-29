package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/26 15:32
 * @description AppraisalSignature
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalSignature {
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 签名
     */
    private String signature;
    /**
     * 月份
     */
    private Integer month;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 签名id
     */
    private Long signatureId;
}
