package com.example.demo.pojo.basePojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 借书返回列表 dto
 */
@Data
public class bookBorrowDto implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -4413372509140096299L;

    /**
     * id
     */
    private String id;

    /**
     * 图书号
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
     * lend是否借出
     */
    private Integer lend;

}
