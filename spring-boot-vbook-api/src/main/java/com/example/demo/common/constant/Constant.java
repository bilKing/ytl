package com.example.demo.common.constant;


/**
 * Created by ytl on 2019/10/9.
 * <p>
 * 常量类
 */
public class Constant {

    /**
     * 权限(设置为0则可以关闭对应权限验证,具体权限值配置在数据库表role中)
     */
    //普通用户
    public static final int user = 0;
    //添加公告权限
    public static final int addAnnouncement = 1;
    //统计权限
    public static final int statistics = 2;
    //用户管理权限
    public static final int userManagement = 4;
    //书籍管理权限
    public static final int bookManagement = 8;
    //权限管理权限
    public static final int jurisdiction = 16;
    //超级管理员 拥有以上全部权限
    public static final int SUPER_MANAGEMENT = 31;

    /**
     * 密码设置
     */
    //密码输入错误进入等待时间所需次数
    public static final int ERROR_TIMES = 5;
    //密码输入错误等待时间(分钟)
    public static final int WAIT_TIME = 5;
    //默认密码
    public static final String DEFAULT_PASSWORD = "111111";

    /**
     * 模板导入注册配置列名 对应 列序号
     */
    //姓名
    public static final int USER_NAME = 0;
    //密码
    public static final int USER_PASSWORD = 1;
    //学号
    public static final int STU_ID = 2;
    //班级
    public static final int USER_CLASS = 3;
    //专业
    public static final int SPECIALITIES = 4;
    //预计毕业年份
    public static final int USER_GRADUATION_YEAR = 5;

    /**
     * 模板导入图书列名 对应 列序号
     */
    //图书名
    public static final int BOOK_NAME = 0;
    //图书号
    public static final int BOOK_ID = 1;
    //图书类型
    public static final int BOOK_TYPE = 2;
    //图书价格
    public static final int BOOK_PRICE = 3;

    /**
     * 删除禁用数据时间点,离禁用时间点 所相隔天数
     */
    //删除禁用用户所相隔时间（天数）
    public static final int DELETE_FORBIDDEN_USER_DAY = 180;
    //删除禁用书籍所相隔时间（天数）
    public static final int DELETE_FORBIDDEN_BOOK_DAY = 180;
    //删除借书记录所相隔时间（天数）
    public static final int DELETE_FORBIDDEN_RECORD_DAY = 180;

    /**
     * 图书借书超期天数 (登录提示超期)
     */
    public static final int BORROW_OUT_TIME = 60;

    /**
     * 循环插入数据库每次条数
     */
    public static final int BATCH_INSERT = 500;

    /**
     * 本地临时缓存导出文件路径
     */
    public static final String BORROW_COUNT = "spring-boot-vbook-consumer\\src\\main\\resources\\static\\CountExport.xls";
    public static final String USER_BORROW_COUNT = "spring-boot-vbook-consumer\\src\\main\\resources\\static\\userBorrowCountExport.xls";
    public static final String BORROW_OUT_TIME_EXPORT = "spring-boot-vbook-consumer\\src\\main\\resources\\static\\borrowOutTimeExport.xls";
    public static final String TOP_LEND_BOOK = "spring-boot-vbook-consumer\\src\\main\\resources\\static\\.topLendBookExport.xls";

    /**
     * 本地模板Excel文件相对路径
     */
    public static final String EXTRA_FORBIDDEN_USERS = "spring-boot-vbook-consumer\\src\\main\\resources\\static\\extraForbiddenUsers.xls";
    public static final String NEW_BOOKS = "spring-boot-vbook-consumer\\src\\main\\resources\\static\\newBooks.xls";
    public static final String NEW_USERS = "spring-boot-vbook-consumer\\src\\main\\resources\\static\\newUsers.xls";

    //======================== 一般常量 ========================

    /**
     * 学号或图书名或图书号
     */
    public static final int IS_STU_ID = 1;
    public static final int IS_BOOK_ID = 2;
    public static final int IS_BOOK_NAME = 3;

    /**
     * state禁用启用
     */
    public static final int ENABLE = 1;
    public static final int PROHIBIT = 0;

}
