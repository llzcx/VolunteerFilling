package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 申诉内容
 * @author 杨世博
 * @date 2023/12/24 10:51
 * @description AppealDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealDto {
    /**
     * 内容
     */
    private String content;
}
