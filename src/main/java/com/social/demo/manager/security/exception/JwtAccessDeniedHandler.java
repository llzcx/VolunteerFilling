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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 拒绝访问
 * @author 陈翔
 */
@Configuration
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //告诉客户端请求未经授权，并根据需要采取相应的措施，例如要求用户进行身份验证、跳转到登录页面或返回自定义的错误信息。
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ApiResp fail = null;
        log.debug("\n------------------Exception Name------------------\n{}\n{}" +
                "\n------------------Exception Name------------------",e.getClass().getName(),e.getMessage());
        //e.printStackTrace();
        if(e instanceof CustomAccessDeniedException){
            //自定义异常
            CustomAccessDeniedException exc = (CustomAccessDeniedException) e;
            fail = ApiResp.fail(exc.getResultCode());
        }else{
            //SpringSecurity内其他异常
            fail = ApiResp.fail(ResultCode.ACCESS_WAS_DENIED);
        }
        outputStream.write(JSONUtil.toJsonStr(fail).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
