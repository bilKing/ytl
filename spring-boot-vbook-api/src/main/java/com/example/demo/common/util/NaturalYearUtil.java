package com.example.demo.common.util;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 闰年判断 工具类
 */
public class NaturalYearUtil {
    public static Boolean naturalYear(int year) {
        if (year % 4 == 0) {
            if ((year % 100 == 0)) {
                if (year % 400 == 0) {
                    return true;
                } else return false;
            } else return true;
        } else return false;
    }
}
