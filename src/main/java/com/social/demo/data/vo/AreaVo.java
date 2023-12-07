package com.social.demo.data.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


/**
 * 地区
 *
 * @author 周威宇
 */
@Data
public class AreaVo {
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
    private List<String> includingProvinces;
    /**
     * 修改时间
     */
    private Timestamp updateTime;
}
