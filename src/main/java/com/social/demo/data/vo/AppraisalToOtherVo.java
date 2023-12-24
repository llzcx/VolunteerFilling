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
public class AppraisalToOtherVo {
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 姓名
     */
    private String name;
    /**
     * 详细
     */
    private List<AppraisalDetailVo> detail;
    /**
     * 月总加分
     */
    private Integer totalPlus;
    /**
     * 月总减分
     */
    private Integer totalMinus;
    /**
     * 月份
     */
    private Integer month;
    /**
     * 上月得分
     */
    private Integer lastScore;
    /**
     * 综测总分
     */
    private Integer score;
}
