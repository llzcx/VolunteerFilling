package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/3/21 15:56
 * @description removeSignatureDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveSignatureDto {
    /**
     * 月份 0-本月
     */
    private Integer month;
    /**
     * 需要被删除的用户的id
     */
    private String userNumber;
}
