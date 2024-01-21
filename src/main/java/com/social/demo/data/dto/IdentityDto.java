package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生身份消息
 *
 * @author 杨世博
 * @date 2023/12/21 22:28
 * @description IdentityVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityDto {
    /**
     * 学号
     */
    private String userNumber;
    /**
     * 身份
     */
    private Integer identity;
}
