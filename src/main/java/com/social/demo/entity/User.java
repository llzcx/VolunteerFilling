package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 陈翔
 * @since 2023-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    /**
     * 学号/工号
     */
    private String userNumber;
    /**
     * 姓名
     */
    private String username;
    /**
     * 性别
     */
    private String sex;
    /**
     * 政治面貌
     */
    private String politicsStatus;
    /**
     * 民族
     */
    private String nation;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    /**
     * 最近修改时间
     */
    private LocalDateTime lastDdlTime;

    /**
     * 角色-测试
     */
    private Integer identity;

    /**
     * 证件照
     */
    private String headshot;

    public User(String userNumber, String username, String password, LocalDateTime created, LocalDateTime lastDdlTime, Integer identity) {
        this.userNumber = userNumber;
        this.username = username;
        this.password = password;
        this.created = created;
        this.lastDdlTime = lastDdlTime;
        this.identity = identity;
    }
}
