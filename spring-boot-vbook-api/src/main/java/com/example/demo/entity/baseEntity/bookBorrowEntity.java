package com.example.demo.entity.baseEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 借书记录表 主体pojo
 */
@Data
@TableName("sys_borrow")
public class bookBorrowEntity implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 861645804416743390L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 图书号
     */
    private String bookId;

    /**
     * 图书名
     */
    private String bookName;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 借书时间
     */
    private String borrowTime;

    /**
     * 还书时间
     */
    private String returnTime;

    /**
     * 禁用启用
     */
    private Integer state;

    /**
     * 禁用时间
     */
    private String forbiddenTime;

    /**
     * 是否借出
     */
    private Integer lend;

    /**
     * 借出天数
     */
    private Integer lendDay;

}
