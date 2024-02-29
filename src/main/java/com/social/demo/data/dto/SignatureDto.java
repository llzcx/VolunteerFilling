package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨世博
 * @date 2024/1/26 17:32
 * @description SignatureDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureDto {
    /**
     * 文件
     */
    private MultipartFile file;
    /**
     * 月份
     */
    private Integer month;
}
