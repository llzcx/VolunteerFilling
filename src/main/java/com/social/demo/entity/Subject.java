package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学科
 *
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    /**
     * 科目id
     */
    private Long subjectId;
    /**
     * 科目名称
     */
    private String name;
}
