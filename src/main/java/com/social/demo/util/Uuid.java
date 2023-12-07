package com.social.demo.util;

import java.util.UUID;

/**
 * @author 周威宇
 */
public class Uuid {
    public static Long getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return Long.valueOf(Integer.valueOf(uuidStr));
    }
}
