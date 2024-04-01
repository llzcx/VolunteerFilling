package com.social.demo.data.dto;

import com.social.demo.data.vo.AppraisalContentVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/4/1 11:21
 * @description AppraisalUploadDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalUploadDto {
    private Integer month;

    private AppraisalContentVo[] appraisalContentVos;
}
