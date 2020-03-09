package com.example.demo.pojo.basePojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/14.
 * <p>
 * 还书返回列表 dto
 */
@Data
public class bookReturnDto implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -6346838457218212386L;

    /**
     * id
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
     * 借书时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String borrowTime;

}
