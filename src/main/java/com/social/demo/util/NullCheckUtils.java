package com.social.demo.util;

import java.lang.reflect.Field;

public class NullCheckUtils {

    public static boolean areAllFieldsNull(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.get(object) != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }  
        }
        return true;
    }
}