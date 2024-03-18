package com.social.demo.util;

import java.lang.reflect.Field;
import java.util.Optional;

public class ValidationUtils {

    public static boolean isAllFieldNull(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value instanceof String) {
                if (Optional.of((String) value).filter(s -> !s.isEmpty()).isPresent()) {
                    return true;
                }
            } else {
                if (Optional.ofNullable(value).isPresent()) {
                    return true;
                }
            }
        }
        return false;
    }
}