package com.example.demo.pojo.basePojo.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 * <p>
 * 统计各专业 用户借书次数
 */
@Data
public class userBorrowDto implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -933369092630118729L;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 借书本数
     */
    private Integer bookNum;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}