package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    /**
     * 小组id
     */
    @TableId(value = "team_id", type = IdType.AUTO)
    private Long teamId;
}
