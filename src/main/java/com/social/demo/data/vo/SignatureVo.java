package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨世博
 * @date 2023/12/21 17:09
 * @description SignatureVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureVo {
    private MultipartFile file;
    private Integer month;
}
