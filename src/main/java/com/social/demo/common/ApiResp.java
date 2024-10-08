package com.social.demo.common;


import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 * @Author: 陈翔
 */
@Data
public class ApiResp<T> implements Serializable {
    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应的数据
     */
    private T data;

    public ApiResp() {
    }

    public ApiResp(boolean success) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.msg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
    }

    public ApiResp(boolean success, ResultCode resultEnum) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.msg = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
    }

    public ApiResp(boolean success, T data) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.msg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
        this.data = data;
    }

    public ApiResp(boolean success, ResultCode resultEnum, T data) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.msg = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
        this.data = data;
    }

    public ApiResp(boolean success, ResultCode resultEnum, String s) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.msg = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : s + resultEnum.getMessage());
    }

    /**
     * 简化控制器if控制返回
     * @param value 条件
     * @param object 条件成功则返回的数据
     * @param resultEnum 失败返回的错误码
     * @return
     */
    public static ApiResp judge(Boolean value, Object object, ResultCode resultEnum){
        if(value){
            return ApiResp.success(object);
        }else{
            return ApiResp.fail(resultEnum);
        }
    }

    /**
     * 简化控制器if控制返回
     * @param value 条件
     * @param objectA 条件成功则返回的数据
     * @param s 条件失败则返回的数据
     * @param resultEnum 失败返回的错误码
     * @return
     */
    public static ApiResp judge(Boolean value, Object objectA, String s, ResultCode resultEnum){
        if(value){
            return ApiResp.success(objectA);
        }else{
            return ApiResp.fail(resultEnum, s);
        }
    }

    public static ApiResp success() {
        return new ApiResp(true);
    }

    public static <T> ApiResp<T> success(T data) {
        return new ApiResp(true, data);
    }

    public static ApiResp fail() {
        return new ApiResp(false);
    }

    public static ApiResp fail(ResultCode resultEnum) {
        return new ApiResp(false, resultEnum);
    }

    public static ApiResp failRegular(String msg){
        return new ApiResp(false,ResultCode.REG_ERROR,msg);
    }

    public static ApiResp failUnKnown(String msg){
        return new ApiResp(false,ResultCode.ERROR_UNKNOWN,msg);
    }
    public static ApiResp fail(String msg) {
        return new ApiResp(false, msg);
    }

    public static ApiResp fail(ResultCode resultEnum, String s) {
        return new ApiResp(false, resultEnum, s);
    }
}