package com.example.demo.pojo.basePojo.form;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/14.
 * <p>
 * 借书列表 form
 */
@Data
public class bookBorrowForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 4726399867835139355L;

    /**
     * 图书类型
     */
    private String bookType;

    /**
     * 图书名
     */
    private String bookName;

    /**
     * 图书号
     */
    private String bookId;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

}
