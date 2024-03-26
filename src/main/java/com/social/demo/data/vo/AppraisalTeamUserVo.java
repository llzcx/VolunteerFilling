package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

/**
 * @author 杨世博
 * @date 2024/3/13 22:53
 * @description AppraisalTeamUserVo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppraisalTeamUserVo {
    private String username;
    private String userNumber;
    private String className;
}
