package com.social.demo.data.vo;

import lombok.Data;

/**
 * 专业
 *
 * @author 周威宇
 */
@Data
public class MajorVo2 {
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
    /**
     * 剩余人数
     */
    private Integer surplusNumber;
    /**
     * 第一志愿
     */
    private Integer first;
    /**
     * 第二志愿
     */
    private Integer second;
    /**
     * 第三志愿
     */
    private Integer third;
}

