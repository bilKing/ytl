package com.example.demo.pojo.exPojo.form;

import com.example.demo.common.constant.Constant;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 *
 * 统计超期未还 form
 *
 */
@Data
public class borrowOutTimeForm implements Serializable {
    private static final long serialVersionUID = 385600429996033133L;
    //是否是学号
    private String isStuId = "true";
    //学号
    @Size(max = 32, message = "学号最长32个字符")
    private String stuId;
    //未还天数
    @Size(max = 4, message = "未还天数最长4个字符")
    private Integer day = Constant.BORROW_OUT_TIME;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;
    /**
     * 当前页
     */
    private long current = 1;
}
