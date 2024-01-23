package com.social.demo.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/22 21:13
 * @description GradeSubjectBo
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GradeSubjectBo {
    /**
     * 科目Id
     */
    private Integer gradeId;
    /**
     * 科目分数
     */
    private Integer grade;
}
