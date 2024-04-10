package com.social.demo.manager.mvc.config;


import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 统一异常处理
 *
 * @author 陈翔
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig {

    /**
     * Exception出错的栈信息转成字符串
     * 用于打印到日志中
     */
    public static String errorStackInfoToString(Throwable e) {
        //try-with-resource语法糖 处理机制
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ignored) {
            throw new RuntimeException(ignored.getMessage(), ignored);
        }
    }

    public static String errorString(Throwable e) {
        //try-with-resource语法糖 处理机制
        return e.getMessage();
    }

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return
     */
    @ExceptionHandler(value = SystemException.class)
    @ResponseBody
    public ApiResp exceptionHandler(SystemException e) {
        return ApiResp.fail(e.getResultCode());
    }


    /**
     * 未知异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResp exceptionHandler(Exception e) {
        // 把错误信息输入到日志中
        String s = errorStackInfoToString(e);
        log.error(s);
        return ApiResp.fail(ResultCode.ERROR_UNKNOWN.getMessage() + errorString(e));
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ApiResp exceptionHandler(NullPointerException e) {
        log.error(errorStackInfoToString(e));
        return ApiResp.fail(ResultCode.NULL_POINT_EXCEPTION);
    }

    /**
     * 参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResp exceptionHandler(MethodArgumentNotValidException e) {
        return ApiResp.fail(ResultCode.PARAM_NOT_VALID);
    }

    /**
     * 参数非法
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ApiResp exceptionHandler(IllegalArgumentException e) {
        log.error(errorStackInfoToString(e));
        return ApiResp.fail(ResultCode.ILLEGAL_PARAMETER);
    }
}
