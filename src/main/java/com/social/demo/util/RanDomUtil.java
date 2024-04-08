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

    public static String CreateUserNumber(String s){
        int len = s.length();
        int randomLength = Math.max(0, 10 - len);
        if (randomLength == 0) {
            return s;
        }

        // 生成随机数字
        Random random = new Random();
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < randomLength; i++) {
            // 生成0-9之间的随机数字
            int randomDigit = random.nextInt(10);
            sb.append(randomDigit);
        }

        // 返回最终的字符串
        return sb.toString();
    }
}
