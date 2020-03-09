package com.example.demo.pojo.basePojo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/16.
 * <p>
 * 某学号或图书号的借书记录 form
 */
@Data
public class lendRecordForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 4471261931315774038L;

    /**
     * 是否是学号
     */
    private String isStuId;


    /**
     * 图书号或学号
     */
    @NotNull(message = "图书号或学号不能为空")
    private String id;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

}
