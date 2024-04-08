package com.social.demo.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/4/8 11:23
 * @description UserMessageBo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageBo {
    private String userNumber;
    private Long userId;
    private String username;
    private Double appraisalScore;
}
