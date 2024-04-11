package com.social.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectUtils {
  
    public static boolean areAllFieldsNull(Object obj) {  
        if (obj == null) {  
            return true; // 或者可以抛出异常，取决于你的需求  
        }  
  
        Class<?> clazz = obj.getClass();  
        Field[] fields = clazz.getDeclaredFields();
  
        for (Field field : fields) {  
            int modifiers = field.getModifiers();  
  
            // 排除private static final字段  
            if (Modifier.isPrivate(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                continue;  
            }  
  
            field.setAccessible(true); // 允许访问私有字段  
  
            try {  
                Object value = field.get(obj);  
  
                // 判断字段值是否为空  
                if (value != null) {  
                    return false; // 如果找到非空字段，立即返回false  
                }  
            } catch (IllegalAccessException e) {  
                e.printStackTrace(); // 应该处理这个异常，这里只是简单打印堆栈跟踪  
            }  
        }  
  
        return true; // 所有字段都为空  
    }
}  