package com.example.demo.common.Enum;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 业务异常枚举
 */

import lombok.Getter;
@Getter
public enum ServiceExceptionEnum {

    /**
     * =================== 请求成功、失败 =============
     */
    SUCCESS(200, "请求成功"),
    ERROR(500, "服务器错误"),

    /**
     * =================== 请求参数 ==================
     */
    PARAM_ERROR(400, "请求参数错误"),
    NOT_A_FUTURE_DATE(420, "日期只能是今天或今天之前的日期"),
    FUTURE_DATE(421, "日期只能是今天或今天之后的日期"),
    YEAR_ERROR(422, "年份必须大于1900,小于2050"),
    BOOK_NAME_OR_ID(423, "图书名或图书id为空"),

    /**
     * =================== 登录注册 ===================
     */
    LOGIN_OVER_TIME(1000, "用户未登录或者登录超时,请退出登录后再重新登录"),
    AUTHENTICATION_EXCEPTION(1001, "用户身份认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),
    VERIFICATION_CODE_ERROR(1003, "验证码错误"),
    USER_NOT_FOUND(1004, "用户不存在"),
    LOGIN_ERROR(1005, "密码错误超过5次，5分钟后才能继续登录"),
    ACCOUNT_IS_FROZEN(1006, "账号暂被冻结，请联系管理员"),
    CONFIRM_PASSWORD_ERROR(1007, "确认密码不一致"),
    INCONSISTENT_OLD_PASSWORD(1008, "旧密码不正确"),
    THIS_PHONE_NUMBER_ALREADY_EXISTS(1009, "该电话号码已存在！"),
    PASSWORD_FALSE(1010, "密码小于6位或大于12位或未以字母下划线开头"),
    STUID_ISEXISTENCE(1011, "已存在该学号"),
    STUID_NULL_OR_PASSWORD_FALSE(1012, "存在未输入学号的用户或密码小于6位或大于12位或未以字母下划线开头"),
    EXCEL_NULL(1013, "EXCEL表内无数据"),
    STUID_NULL(1014, "存在未输入学号的用户"),
    USER_EMPTY(1015, "未输入用户id"),
    PASS_WORD_EMPTY(1016, "未输入用户密码"),
    USER_GRADUATION_YEAR_EMPTY(1017, "未输入预计毕业年份"),
    USER_SPECIALITIES_EMPTY(1018, "未输入专业"),

    /**
     * =================== 数据库 ====================
     */
    DB_DUPLICATE_KEY(2001, "数据库中已存在该记录"),
    DB_NO_DATA(2002, "数据库中不存在该记录"),

    /**
     * =================== 网络请求 ===================
     */
    NETWORK_REQUEST_TIMEOUT(6001, "网络请求超时，请稍后再试"),
    FILE_FALSE(6002, "文件上传失败"),
    FILE_FORMAT_FALSE(6003, "文件格式不是xls"),
    FILE_DOWN_LOAD_FALSE(6004, "文件下载失败"),

    /**
     * =================== 图书添加 ===================
     */
    BOOK_ID_OR_ADDRESS_NULL(7001, "存在图书id为空的书籍 或者文件为空"),
    BOOK_ID_Lend(7002, "此书已借出"),
    BOOK_ID_NULL(7003, "存在图书id为空的书籍"),

    /**
     * =================== 导出 ===================
     */
    EXPORT_ERROR(8001, "导出失败"),

    /**
     * =================== 权限 ===================
     */
    INSUFFICIENT_PERMISSIONS(9001, "账号权限不足，请联系管理员"),
    THIS_PERMISSIONS_NULL(9002, "用户无此权限"),
    THIS_PERMISSIONS_EXISTENCE(9003, "用户已拥有此权限"),

    /**
     * =================== 公告 ===================
     */
    NAME_NULL(10001, "没有设置管理员名字"),

    /**
     * =================== 禁用 ===================
     */
    FORBIDDEN_FALSE(11001, "禁用失败");

    private int code;
    private String message;

    ServiceExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
