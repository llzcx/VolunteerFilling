package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 杨世博
 * @date 2024/1/23 21:57
 * @description AppraisalTeamVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppraisalTeamVo {
    private Long userId;
    private String username;
    private String userNumber;
}
