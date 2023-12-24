package com.social.demo.entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author 杨世博
 * @date 2023/12/4 14:34
 * @description 科目组合
 */
@Data
public class SubjectGroup {
    /**
     * 组合id
     */
    private Long groupId;
    /**
     * 学科
     */
    private List<String> subjects;
}
