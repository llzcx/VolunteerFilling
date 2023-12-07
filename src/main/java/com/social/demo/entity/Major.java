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
     * 地区选择id
     */
    private Long areaChooseId;
    /**
     * 院校编码
     */
    private String schoolName;
    /**
     * 专业名称
     */
    private String name;
    /**
     * 所属学院
     */
    private String college;
    /**
     * 选科id
     */
    private String chooseId;
}
