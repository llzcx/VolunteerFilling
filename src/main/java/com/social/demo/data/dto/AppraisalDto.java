package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/20 20:04
 * @description AppraisalDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalDto {
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 详细
     */
    private List<AppraisalDetailDto> appraisalDetails;
    /**
     * 月份
     */
    private Integer month;
}
