package com.social.demo.data.dto;

import com.social.demo.data.bo.GradeSubjectBo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨世博
 * @date 2024/1/22 21:12
 * @description GradeDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
    /**
     * 用户名
     */
    private String userNumber;
    /**
     * 科目成绩
     */
    private List<GradeSubjectBo> gradeSubjectBos;
    /**
     * 总成绩
     */
    private Double score;
}
