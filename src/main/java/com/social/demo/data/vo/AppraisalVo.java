package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/19 15:50
 * @description AppraisalVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalVo {
    /**
     * 月份
     */
    private Integer month;
    /**
     * 综测总分
     */
    private Double score;
    /**
     * 签名
     */
    private String signature;
    /**
     * 内容
     */
    private AppraisalContentVo content;
    /**
     * 合计
     */
    private AppraisalTotalVo total;
    /**
     * 是否结束
     */
    private Boolean isEnd;
}
