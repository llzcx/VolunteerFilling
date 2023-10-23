package com.social.demo.manager.security.exception;

import com.social.demo.common.ResultCode;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.AuthenticationException;

/**
 * 自定义SpringSecurity业务异常类：认证失败时
 * @author 陈翔
 */
@EqualsAndHashCode(callSuper = true)
public class CustomAuthenticationException extends AuthenticationException {

    private final ResultCode resultCode;

    public CustomAuthenticationException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}