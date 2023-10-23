package com.social.demo.util;

import com.social.demo.constant.RegConstant;

import java.util.Random;

/**
 * 生成工具类
 * @author 陈翔
 */
public class RanDomUtil {

    public static String generateCode() {
        String regex = RegConstant.PHONE_CODE;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < 5) {
            int asciiValue = random.nextInt(123);
            if ((asciiValue >= 48 && asciiValue <= 57)) {
                char character = (char) asciiValue;
                sb.append(character);
            }
        }

        String randomString = sb.toString();

        if (randomString.matches(regex)) {
            return randomString;
        } else {
            return generateCode();
        }
    }

}
