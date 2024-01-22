package com.social.demo.common;

import com.social.demo.constant.PropertiesConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 陈翔
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Identity {
    // 定义注解的成员变量
    int value() default PropertiesConstant.IDENTITY_STUDENT;
}
