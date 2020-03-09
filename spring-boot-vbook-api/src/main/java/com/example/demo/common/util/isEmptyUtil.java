package com.example.demo.common.util;

import java.util.List;

/**
 * Created by ytl on 2019/10/14.
 * <p>
 * 为空判断 工具类
 */
public class isEmptyUtil {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof List)) {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String)) {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        if ((obj instanceof List)) {
            return ((List) obj).size() != 0;
        }
        if ((obj instanceof String)) {
            return !((String) obj).trim().equals("");
        }
        return true;
    }

}
