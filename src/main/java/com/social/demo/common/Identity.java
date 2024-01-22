package com.social.demo.common;

import com.social.demo.constant.PropertiesConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 陈翔
 */
// 定义一个自定义注解
@Retention(RetentionPolicy.RUNTIME) // 指定注解的保留期限
@Target(ElementType.METHOD) // 指定注解可以应用于方法
public @interface Identity {
    // 定义注解的成员变量
    int value() default PropertiesConstant.IDENTITY_STUDENT;
}
