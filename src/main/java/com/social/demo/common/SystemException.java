package com.social.demo.common;


import lombok.EqualsAndHashCode;

/**
 * 自定义SpringMVC业务异常类
 * @author 陈翔
 */

@EqualsAndHashCode(callSuper = true)
public class SystemException extends RuntimeException{

    private ResultCode resultCode;
    private Integer code;
    private String msg;

    public SystemException() {
        super();
    }

    public SystemException(ResultCode resultCode) {
        super("{code:" + resultCode.getCode() + ",Msg:" + resultCode.getMessage() + "}");
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public SystemException(Integer code, String msg) {
        super("{code:" + code + ",Msg:" + msg + "}");
        this.code = code;
        this.msg = msg;
    }

    public SystemException(Integer code, String msg, Object... args) {
        super("{code:" + code + ",Msg:" + String.format(msg, args) + "}");
        this.code = code;
        this.msg = String.format(msg, args);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
