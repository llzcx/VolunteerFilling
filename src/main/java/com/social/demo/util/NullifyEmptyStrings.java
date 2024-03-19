package com.social.demo.util;

import java.lang.reflect.Field;

public class NullifyEmptyStrings {

    public static void nullifyEmptyStringsInObject(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.getType().equals(String.class)) {
                    f.setAccessible(true);
                    String value = (String) f.get(obj);
                    if (value != null && value.isEmpty()) {
                       f.set(obj, null);
                    }
                }
            }
        } catch (Exception e) {
            // handle error
            e.printStackTrace();
        }
    }
}