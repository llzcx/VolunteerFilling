package com.social.demo.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 杨世博
 * @date 2024/1/22 21:13
 * @description GradeSubjectBo
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GradeSubjectBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 科目Id
     */
    private Long gradeId;
    /**
     * 科目分数
     */
    private Integer grade;
}
