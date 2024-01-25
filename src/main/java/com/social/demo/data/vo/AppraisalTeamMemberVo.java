package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/24 14:57
 * @description AppraisalTeamMemberVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalTeamMemberVo {
    private Long userId;
    private String username;
    private String userNumber;
}
