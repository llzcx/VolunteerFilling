package com.social.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 时间工具类
 */
public class TimeUtil {
    /**
     * 获取当前时间的 LocalDateTime 类型
     * @return 当前时间的 LocalDateTime
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 将形如yyyy-MM-dd的日期格式转化为 LocalDateTime 类型
     * @param dateString yyyy-MM-dd格式的日期
     * @return 转换后的 LocalDateTime
     * @throws DateTimeParseException 解析异常
     */
    public static LocalDateTime convertStringToTimestamp(String dateString) throws DateTimeParseException {
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 解析日期字符串为 LocalDateTime 对象
        return LocalDateTime.parse(dateString, formatter);
    }

    /**
     * 以文件夹的形式生成日期
     * @return 当前日期的文件夹形式，例如: 2023/07/20
     */
    public static String getCurrentDateDirectory() {
        // 获取当前日期
        LocalDateTime currentDate = LocalDateTime.now();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // 格式化日期为字符串
        return currentDate.format(formatter);
    }
}
