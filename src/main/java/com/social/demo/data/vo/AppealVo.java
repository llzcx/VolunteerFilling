package com.social.demo.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 杨世博
 * @date 2023/12/22 15:53
 * @description AppealVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealVo {
    /**
     * 申诉id
     */
    private Long appealId;
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 姓名
     */
    private String username;
    /**
     * 内容
     */
    private String content;
    /**
     * 类型
     */
    private Boolean type;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    /**
     * 最近修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastDdlTime;
    /**
     * 申诉状态
     * 0- 待处理
     * 1- 已处理
     * 2- 已取消
     */
    private Integer state;
}
