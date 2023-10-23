package com.social.demo.manager.security.exception;

import cn.hutool.json.JSONUtil;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 身份认证时出错
 * @author 陈翔
 */
@Configuration
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //告诉客户端请求未经授权，并根据需要采取相应的措施，例如要求用户进行身份验证、跳转到登录页面或返回自定义的错误信息。
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiResp fail = null;
        log.debug("\n------------------Exception Name------------------\n{}\n{}" +
                "\n------------------Exception Name------------------",e.getClass().getName(),e.getMessage());
        //e.printStackTrace();
        if(e instanceof CustomAuthenticationException){
            //自定义异常
            CustomAuthenticationException exc = (CustomAuthenticationException) e;
            fail = ApiResp.fail(exc.getResultCode());
        }else{
            //SpringSecurity内其他异常
            fail = ApiResp.fail(ResultCode.AUTHENTICATION_EXCEPTION);
        }
        outputStream.write(JSONUtil.toJsonStr(fail).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
