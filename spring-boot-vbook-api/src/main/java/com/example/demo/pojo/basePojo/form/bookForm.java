package com.example.demo.pojo.basePojo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 添加图书 form
 */
@Data
public class bookForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 4832683829754370939L;

    /**
     * 图书id
     */
    @Size(max = 32, message = "图书id最长32个字符")
    @NotNull(message = "图书id不能为空")
    private String bookId;

    /**
     * 图书类型
     */
    @Size(max = 16, message = "图书类型最长16个字符")
    private String bookType;

    /**
     * 图书名
     */
    @Size(max = 16, message = "图书名最长16个字符")
    private String bookName;

    /**
     * 图书名价格
     */
    @Size(max = 16, message = "图书价格最长16个字符")
    private String bookPrice;

}
