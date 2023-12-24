package com.social.demo.entity;

import lombok.Data;

import java.sql.Timestamp;


/**
 * 地区
 *
 * @author 周威宇
 */
@Data
public class Area {
    /**
     * 地区id
     */
    private Long areaId;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 包含省份
     */
    private String includingProvinces;
    /**
     * 修改时间
     */
    private Timestamp updateTime;
    /**
     * 科目范围
     */
    private String subjectScope;
    /**
     * 选科数量
     */
    private Integer subjectNumber;

}
