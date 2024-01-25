package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 陈翔
 */
@Data
@TableName("sys_api")
@ToString
public class SysApi {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 接口样式
     */
    private String pattern;
    /**
     * 描述
     */
    private String description;


    private List<SysRoleVo> roles;
}