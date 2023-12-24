package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 综测详细
 *
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalDetail {
    /**
     * 综测详细id
     */
    @TableId(value = "appraisal_detail_id", type = IdType.AUTO)
    private Long appraisalDetailId;
    /**
     * 综测id
     */
    private Long appraisalId;
    /**
     * 类别：德育、美育......
     */
    private Integer classes;
    /**
     * 加分
     */
    private Integer bonusPoints;
    /**
     * 减分
     */
    private Integer subtractPoints;
    /**
     * 说明
     */
    @TableField(value = "`explain`")
    private String explain;
}
