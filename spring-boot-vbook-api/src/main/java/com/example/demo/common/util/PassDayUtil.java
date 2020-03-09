package com.example.demo.common.util;


import java.time.LocalDateTime;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 经过天数 工具类
 */
public class PassDayUtil {
    public static int passYearUtil(LocalDateTime before, LocalDateTime after) {
        if (before.getYear() != after.getYear()) {
            int passYear = after.getYear() - before.getYear();
            int passDay = (NaturalYearUtil.naturalYear(before.getYear()) ? 366 : 365) - before.getDayOfYear() + after.getDayOfYear();
            if (passYear != 1) {
                int x = 1;
                while (passYear - 1 != 0) {
                    passDay += NaturalYearUtil.naturalYear(after.getYear() - x) ? 366 : 365;
                    x++;
                    passYear--;
                }
                return passDay;
            } else return passDay;
        } else return after.getDayOfYear() - before.getDayOfYear();
    }
}
