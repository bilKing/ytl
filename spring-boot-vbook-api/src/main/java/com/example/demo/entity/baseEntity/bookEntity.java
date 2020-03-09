package com.example.demo.entity.baseEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 图书表 主体pojo
 */
@Data
@TableName("sys_book")
public class bookEntity implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 4965577518552479630L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 图书id
     */
    private String bookId;

    /**
     * 图书类型
     */
    private String bookType;

    /**
     * 图书名
     */
    private String bookName;

    /**
     * 最新一次借出时间
     */
    private String borrowBookTime;

    /**
     * 最新一次归还时间
     */
    private String returnBookTime;

    /**
     * 最新一次借书人
     */
    private String borrowUser;

    /**
     * 是否借出
     */
    private Integer lend;

    /**
     * 禁用启用
     */
    private Integer state;

    /**
     * 创建图书年份
     */
    private Integer bookAddYear;

    /**
     * 禁用时间
     */
    private String forbiddenTime;

    /**
     * 图书价格
     */
    private String bookPrice;

}
