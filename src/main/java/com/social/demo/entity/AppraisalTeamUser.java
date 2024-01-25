package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/23 21:34
 * @description AppraisalTeamUser
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalTeamUser {
    /**
     * 小组成员id
     */
    private Long teamUserId;
    /**
     * 班级成员id
     */
    private Long classUserId;
}
