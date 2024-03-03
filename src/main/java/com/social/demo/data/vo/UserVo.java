package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 杨世博
 * @date 2023/12/4 21:37
 * @description UserVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    /**
     * userId
     */
    private Long userId;
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 姓名
     */
    private String username;
    /**
     * 班级
     */
    private String className;
    /**
     * 用户身份
     */
    private String role;
    /**
     * 最近操作时间
     */
    private LocalDateTime lastDdlTime;
}
