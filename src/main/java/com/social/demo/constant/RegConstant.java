package com.social.demo.constant;

/**
 * 存放参数校验的dto
 * @author 陈翔
 */
public interface RegConstant {
    /**
     * 可以以 "+86"、"0086" 或者不带国家代码的形式进行验证。
     * 必须以 1 开头。
     * 第二位数字可以是 3-9 之间的数字。
     * 剩余的 9 位数字可以是 0-9 之间的任意数字。
     */
   String PHONE = "^(?:(?:\\\\+|00)86)?1[3-9]\\\\d{9}$";

    /**
     * 至少包含一个大写字母 [A-Z]。
     * 至少包含一个小写字母 [a-z]。
     * 至少包含一个数字 [\\\\d]。
     * 至少包含一个特殊字符 [@#$%^&+=]。
     * 不包含空格 (?=\\\\S+$)。
     * 长度至少为8位。
     */
   String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    /**
     * 只能包含字母、数字、下划线和连字符（减号）。
     * 必须以字母开头。
     * 长度在3到16个字符之间
     */
   String USERNAME = "^[a-zA-Z][a-zA-Z0-9_-]{2,15}$";

    /**
     * 微信号
     */
   String WECHAT = "^[a-zA-Z][-_a-zA-Z0-9]{5,19}$";
    /**
     * 一个字母、数字或下划线作为昵称的字符。
     * 限制昵称的长度在3到16个字符之间。
     */
   String NICK_NAME = "^[a-zA-Z0-9_]{3,16}$";

    /**
     * 网址
     */
   String URL = "^(http|https)://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(/[a-zA-Z0-9-]*)*(\\?[a-zA-Z0-9-]+=[a-zA-Z0-9-%]+(&[a-zA-Z0-9-]+=[a-zA-Z0-9-%]+)*)?$";


    /**
     * 手机验证码格式 5个数字
     */
   String PHONE_CODE = "^[0-9]{5}$";

    /**
     * 图片验证码格式
     */
   String IMG_CODE = "^[A-Za-z0-9]{5}$";

    /**
     * 经度,纬度
     */
   String GEOHASH = "^-?\\d{1,3}\\.\\d+,-?\\d{1,2}\\.\\d+$";
}
