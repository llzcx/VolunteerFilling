package com.social.demo.data.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2023/12/5 16:20
 * @description ClassVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassVo {
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 工号
     */
    private String userNumber;
    /**
     * 班主任名
     */
    private String username;
    /**
     * 班级名
     */
    private String className;
    /**
     * 班级人数
     */
    private Integer size;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 班主任联系方式
     */
    private String phone;
}
