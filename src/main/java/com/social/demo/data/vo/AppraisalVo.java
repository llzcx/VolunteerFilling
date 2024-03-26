package com.social.demo.data.vo;

import com.social.demo.constant.PropertiesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
