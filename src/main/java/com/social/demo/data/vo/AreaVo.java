package com.social.demo.data.vo;

import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

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
    /**
     * 科目范围
     */
    private List<String> subjectScope;
    /**
     * 选科数量
     */
    private Integer subjectNumber;
}
