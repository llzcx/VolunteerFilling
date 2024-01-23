package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/22 20:21
 * @description GradeSubjectDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeSubjectDto {
    /**
     * 成绩科目名称
     */
    private String gradeName;
}
