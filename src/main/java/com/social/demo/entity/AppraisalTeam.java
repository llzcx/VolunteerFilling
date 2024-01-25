package com.social.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/1/23 20:45
 * @description AppraisalTeam
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppraisalTeam {
    /**
     * 班级id
     */
    private Long classId;
    /**
     * 小组成员id
     */
    private Long teamUserId;
}
