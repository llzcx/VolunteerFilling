package com.social.demo.entity;

/**
 * 综测详细
 *
 * @author 杨世博
 */
public class AppraisalDetail {
    /**
     * 综测详细id
     */
    private Long id;
    /**
     * 综测id
     */
    private Long appraisalId;
    /**
     * 类别：德育、美育......
     */
    private Boolean classes;
    /**
     * 类型：加分、减分
     */
    private Boolean type;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 说明
     */
    private String explain;
}
