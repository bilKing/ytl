package com.example.demo.common.util;


/**
 * Created by ytl on 2019/10/8.
 * <p>
 * uuid工具类
 */
public class UUID {

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

}
