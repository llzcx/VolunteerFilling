package com.social.demo.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;

/**
 * @author 陈翔
 */
public class AgeCalculator {
    public static int calculateAge(Timestamp timestamp) {
        // 将 Timestamp 对象转换为 LocalDate 对象
        LocalDate birthDate = timestamp.toLocalDateTime().toLocalDate();

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 使用 Period 类计算年龄差距
        Period period = Period.between(birthDate, currentDate);

        // 获取计算出的年龄差值中的年份部分
        int age = period.getYears();

        return age;
    }

}
