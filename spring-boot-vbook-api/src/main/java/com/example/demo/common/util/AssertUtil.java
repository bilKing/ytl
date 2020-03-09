package com.example.demo.common.util;

import com.example.demo.common.Enum.ServiceExceptionEnum;
import com.example.demo.common.Exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ytl on 2019/10/10.
 * <p>
 * 断言工具类
 */
public class AssertUtil {
    //============================日期===========================

    /**
     * 断言不能为未来的日期
     *
     * @param dates
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertNotAFutureDate(LocalDate... dates) {
        if (dates.length != 0) {
            for (int y = 0; y < dates.length; y++) {
                if (isEmptyUtil.isNotEmpty(dates[y])) {
                    if (dates[y].isAfter(LocalDate.now())) {
                        throw new ServiceException(ServiceExceptionEnum.NOT_A_FUTURE_DATE);
                    }
                }
            }
        }
    }

    /**
     * String转LocalDate
     *
     * @param date
     * @throws ServiceException 断言失败抛业务异常
     */
    public static LocalDate StringToLocalDate(String date) {
        LocalDate dateFormat = null;
        if (isEmptyUtil.isNotEmpty(date)) {
            try {
                dateFormat = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR);
            }
        }
        return dateFormat;
    }

    /**
     * String转LocalDateTime
     *
     * @param date
     * @throws ServiceException 断言失败抛业务异常
     */
    public static LocalDateTime StringToLocalDateTime(String date) {
        LocalDateTime dateFormat = null;
        if (isEmptyUtil.isNotEmpty(date)) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateFormat = LocalDateTime.parse(date, formatter);
            } catch (Exception e) {
                throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR);
            }
        }
        return dateFormat;
    }

    /**
     * 断言为未来的日期
     *
     * @param date
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertFutureDate(LocalDate date, String msg) {
        if (date.isBefore(LocalDate.now())) {
            if (StringUtils.isNotBlank(msg)) {
                throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
            } else {
                throw new ServiceException(ServiceExceptionEnum.FUTURE_DATE);
            }
        }
    }

    /**
     * 断言时间顺序
     *
     * @param before
     * @param after
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertDateTimeOrder(LocalDateTime before, LocalDateTime after, String msg) {
        if (before.isAfter(after)) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
        }
    }

    //============================对象===========================

    /**
     * 断言不能为null
     *
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertNotNull(Object o, String msg) {
        if (o == null) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
        }
    }

    /**
     * 断言不能不为null
     *
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertNull(Object o, String msg) {
        if (o != null) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
        }
    }

    /**
     * 断言不能为null 异常枚举
     *
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertNotNull(Object o, ServiceExceptionEnum exceptionEnum) {
        if (o == null) {
            throw new ServiceException(exceptionEnum);
        }
    }

    //============================字符串===========================

    /**
     * 断言不能为空串
     *
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertNotBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
        }
    }

    /**
     * 断言不能为空串 异常枚举
     *
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertNotBlankEnum(String str, ServiceExceptionEnum exceptionEnum) {
        if (StringUtils.isBlank(str)) {
            throw new ServiceException(exceptionEnum);
        }
    }

    /**
     * 断言字符串长度等于多少
     *
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertStringLength(String str, long length, String msg) {
        if (str.length() != length) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
        }
    }

    /**
     * 断言字符串长度在一定范围内
     *
     * @throws ServiceException 断言失败抛业务异常
     */
    public static void AssertStringLength(String str, long min, long max, String msg) {
        if (str == null || str.length() < min || str.length() > max) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
        }
    }

    public static void isTrue(boolean condition, String msg) {
        if (!condition) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR.getCode(), msg);
        }
    }

    //============================为数字===========================
    //为数字并判断非空
    public static void isNumber(String... dates) {
        if (dates.length != 0) {
            try {
                for (int y = 0; y < dates.length; y++) {
                    if (isEmptyUtil.isNotEmpty(dates[y])) {
                        Integer.parseInt(dates[y]);
                    } else {
                        throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR);
                    }
                }
            } catch (Exception e) {
                throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR);
            }
        }
    }

    //年份判断
    public static void isYear(int... dates) {
        if (dates.length != 0) {
            int start = 1900;
            int end = 2050;
            for (int y = 0; y < dates.length; y++) {
                if (isEmptyUtil.isNotEmpty(dates[y])) {
                    if (dates[y] < start || dates[y] > end) {
                        throw new ServiceException(ServiceExceptionEnum.YEAR_ERROR);
                    }
                }
            }
        }
    }

}
