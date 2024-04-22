package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理员展示类
 *
 * @author 杨世博
 * @date 2024/4/22 20:36
 * @description AdminVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVo {
    /**
     * 用户名
     */
    private String userNumber;
    /**
     * 上次登录时间
     */
    private LocalDateTime lastDdlTime;
}
