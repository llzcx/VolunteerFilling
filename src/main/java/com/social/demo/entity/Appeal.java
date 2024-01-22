package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 申诉
 *
 * @author 杨世博
 * @date 2023/12/22 15:35
 * @description Appeal
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appeal {
    /**
     * 申诉id
     */
    @TableId(value = "appeal_id", type = IdType.AUTO)
    private Long appealId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    @TableField(value = "`state`")
    /**
     * 申诉状态
     * 0- 待处理
     * 1- 已处理
     * 2- 已取消
     */
    private Integer state;
    /**
     * 最近修改时间
     */
    private LocalDateTime lastDdlTime;
    /**
     * 申述类型 0-综测 1-志愿
     */
    private Boolean type;

    public Appeal(Long userId, Long classId, String content, LocalDateTime created, Integer state, LocalDateTime lastDdlTime) {
        this.userId = userId;
        this.classId = classId;
        this.content = content;
        this.created = created;
        this.state = state;
        this.lastDdlTime = lastDdlTime;
    }
}
