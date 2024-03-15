package com.social.demo.data.vo;

import lombok.Data;

import java.util.List;

/**
 * 科目规则
 * @author 周威宇
 */
@Data
public class SubjectRuleVo {
    /**
     *地区Id
     */
    private Long areaId;
    /**
     *必选科目
     */
    private List<String> requiredSubjects;
    /**
     *任选科目
     */
    private OptionalSubject optionalSubjects;
    /**
     * 科目组合范围
     */
    private List<Integer> subjectGroups;
    /**
     *前端字符串
     */
    private List<String> strings;
}
