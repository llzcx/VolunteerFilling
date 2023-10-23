package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 陈翔
 */
@TableName("role")
@Data
public class SysRole {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("role_name")
    private String roleName;

    private String description;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("update_time")
    private Timestamp updateTime;

    private Boolean status;

}