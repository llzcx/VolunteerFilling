package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成绩科目
 *
 * @author 杨世博
 * @date 2024/1/22 20:00
 * @description GradeSubject
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeSubject {
    /**
     * 成绩科目id
     */
    @TableId(value = "grade_id", type = IdType.AUTO)
    private Long gradeId;
    /**
     * 成绩科目名称
     */
    private String gradeName;
}
