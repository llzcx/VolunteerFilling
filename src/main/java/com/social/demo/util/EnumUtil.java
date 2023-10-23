package com.social.demo.util;


import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;

import java.lang.reflect.Field;

/**
 * @author 陈翔
 */
public class EnumUtil {

    /**
     * 通过code获取对应的枚举类的值
     * @param code
     * @param enumClass
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> E getEnumByCode(Integer code, Class<E> enumClass) {
        try {
            Field[] fields = enumClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isEnumConstant()) {
                    E item = (E) field.get(null);
                    if (getCodeValue(item).equals(code)) {
                        return item;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new SystemException(ResultCode.PARAM_NOT_VALID);
    }

    /**
     * 获取私有字段code
     * @param item
     * @param <E>
     * @return
     */
    private static <E extends Enum<E>> Integer getCodeValue(E item) {
        try {
            Field codeField = item.getClass().getDeclaredField("code");
            codeField.setAccessible(true);
            return (Integer) codeField.get(item);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
