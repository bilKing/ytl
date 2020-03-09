package com.example.demo.pojo.exPojo.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 *
 * 统计超期未还 dto
 *
 */
@Data
public class borrowOutTimeDto implements Serializable {
    private static final long serialVersionUID = -6459710473988872152L;
    //学号
    private String stuId;
    //图书号
    private String bookId;
    //借书天数
    private Integer borrowDay;

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
