package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学校信息
 *
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    /**
     *
     */
    @TableId(value = "school_id", type = IdType.AUTO)
    private Long schoolId;
    /**
     * 院校编码
     */
    private Long number;
    /**
     * 学校名称
     */
    private String name;

    public School(Long number, String name) {
        this.number = number;
        this.name = name;
    }
}
