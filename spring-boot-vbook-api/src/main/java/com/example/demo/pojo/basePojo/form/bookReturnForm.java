package com.example.demo.pojo.basePojo.form;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/14.
 * <p>
 * 还书返回列表 form
 */
@Data
public class bookReturnForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -1694831038767580970L;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

}
