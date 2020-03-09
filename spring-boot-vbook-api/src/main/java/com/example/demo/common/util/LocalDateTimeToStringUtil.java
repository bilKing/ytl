package com.example.demo.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ytl on 2019/10/14.
 * <p>
 * 获取String类型now 工具类
 */
public class LocalDateTimeToStringUtil {

    //获取当前LocalDateTime的String
    public static String formatter() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    //获取当前LocalDate的String
    public static String formatterDay() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

}
