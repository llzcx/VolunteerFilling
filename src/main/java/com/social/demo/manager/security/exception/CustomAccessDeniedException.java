package com.social.demo.manager.security.exception;

import com.social.demo.common.ResultCode;
import lombok.EqualsAndHashCode;
import org.springframework.security.access.AccessDeniedException;


/**
 * 区别于SpringMVC全局异常处理
 * 为什么要定义2个多余的异常类呢？
 * 原因：SpringSecurity内部处理只能接受AccessDeniedException异常和AuthenticationException异常
 * 自定义SpringSecurity业务异常类:当用户访问无权限资源时
 * @author 陈翔
 */
@EqualsAndHashCode(callSuper = true)
public class CustomAccessDeniedException extends AccessDeniedException {

    private final ResultCode resultCode;

    public CustomAccessDeniedException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode() {
        return resultCode;
    }
}