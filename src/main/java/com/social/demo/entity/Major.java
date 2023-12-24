package com.social.demo.entity;

import lombok.Data;

/**
 * 专业
 *
 * @author 周威宇
 */
@Data
public class Major {
    /**
     * 专业id
     */
    private Long majorId;
    /**
     * 院校编码
     */
    private Long schoolId;
    /**
     * 专业名称
     */
    private String name;
    /**
     * 所属学院
     */
    private String college;
    /**
     * 选科规则
     */
    private String subjectRule;
    /**
     * 招生人数
     */
    private Integer enrollmentNumber;

}
