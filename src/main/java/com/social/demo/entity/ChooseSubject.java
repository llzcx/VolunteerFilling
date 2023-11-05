package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专业选择方案
 *
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseSubject {
    /**
     * 学科id
     */
    private Long subjectId;
    /**
     * 选科id
     */
    private Long chooseId;
}
