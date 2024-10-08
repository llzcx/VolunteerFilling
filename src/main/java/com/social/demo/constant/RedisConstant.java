package com.social.demo.constant;

/**
 * redis常量
 * @author 陈翔
 */
public interface RedisConstant {
    String WISH = "WISH:";

    String JWT_TOKEN = "JWT_TOKEN:";

    String ACCESS_TOKEN = "ACCESS_TOKEN:";

    String REFRESH_TOKEN = "REFRESH_TOKEN:";

    String LOGIN_CODE = "LOGIN_CODE:";
    String REGISTER_CODE = "REGISTER_CODE:";

    String FORGET_CODE = "FORGET_CODE:";

    /**
     * token黑名单
     */
    String TOKEN_BLACKLIST_PREFIX = "TOKEN_BLACKLIST_PREFIX:";

    /**
     * 手机验证验证码有效期五分钟
     */
    Integer PHONE_CODE_EXPIRE =  5 * 60;

    /**
     * 图片验证验证码有效期30秒
     */
    Integer IMG_CODE_EXPIRE =  30;
}
