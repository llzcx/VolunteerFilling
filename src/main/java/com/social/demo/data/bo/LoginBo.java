package com.social.demo.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.el.parser.Token;

/**
 * @author 杨世博
 * @date 2023/11/28 20:54
 * @description LoginVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBo {
    /**
     * token
     */
    private TokenPair token;
    /**
     * 角色
     */
    private String role;
}
