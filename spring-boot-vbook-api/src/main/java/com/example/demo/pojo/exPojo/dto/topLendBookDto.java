package com.example.demo.pojo.exPojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 *
 * 统计被借数量最多的图书
 *
 */
@Data
public class topLendBookDto implements Serializable {
    private static final long serialVersionUID = -1046103457577610555L;
    //图书名
    private String bookName;
    //借出次数
    private Integer lendTimes;
}
