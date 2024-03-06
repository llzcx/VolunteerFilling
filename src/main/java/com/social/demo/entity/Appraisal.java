package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 综测
 *
 * @author 杨世博
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appraisal {
    /**
     * 综测id
     */
    @TableId(value = "appraisal_id", type = IdType.AUTO)
    private Long appraisalId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 月份
     */
    @TableField(value = "`month`")
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
    private String content;
    /**
     * 每类分数合计
     */
    private String total;

    public Appraisal(Long userId, Integer month, Double score) {
        this.userId = userId;
        this.month = month;
        this.score = score;
    }

    public Appraisal(Long userId, Integer month) {
        this.userId = userId;
        this.month = month;
    }
}
