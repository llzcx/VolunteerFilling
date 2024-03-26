package com.social.demo.util;

import java.lang.reflect.Field;

public class ConvertNullToEmptyString {

    public static void convert(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.getType().equals(String.class)) {
                    f.setAccessible(true);
                    Object value = f.get(obj);
                    if (value == null) {
                        f.set(obj, "");
                    }
                }
            }
        } catch (Exception e) {
            // handle error
            e.printStackTrace();
        }
    }
}