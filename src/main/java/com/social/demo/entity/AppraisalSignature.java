package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "signature_id", type = IdType.AUTO)
    private Long signatureId;

    public AppraisalSignature(Long classId, String signature, Integer month, Long userId) {
        this.classId = classId;
        this.signature = signature;
        this.month = month;
        this.userId = userId;
    }
}
