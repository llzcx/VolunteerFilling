package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2023/12/19 15:58
 * @description AppraisalDetailVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalDetailVo {
    /**
     * 类型
     * 1-德育
     * 2-智育
     * 3-体育
     * 4-美育
     * 5-劳动
     */
    private Integer classes;
    /**
     * 加分详细
     */
    private String plusExplain;
    /**
     * 减分详细
     */
    private String minusExplain;
    /**
     * 加分
     */
    private Integer bonusPoints;
    /**
     * 减分
     */
    private Integer subtractPoints;
}
