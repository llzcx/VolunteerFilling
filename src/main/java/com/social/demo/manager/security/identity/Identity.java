package com.social.demo.manager.security.identity;

import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识什么身份的用户可以使用此接口
 * @author 陈翔
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Identity {
    // 接口默认为学生
    IdentityEnum[] value() default IdentityEnum.STUDENT;
}
