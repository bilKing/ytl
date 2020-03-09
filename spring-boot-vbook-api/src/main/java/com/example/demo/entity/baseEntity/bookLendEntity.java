package com.example.demo.entity.baseEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by ytl on 2019/10/17.
 * <p>
 * 借书表 主体pojo
 */
@Data
@TableName("sys_lend")
public class bookLendEntity {

    /**
     * 主键id
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
     * 学号
     */
    private String stuId;

    /**
     * 借书时间
     */
    private String borrowTime;

    /**
     * 还书时间
     */
    private String returnTime;

    /**
     * 是否借出
     */
    private Integer lend;

    /**
     * 借出天数
     */
    private Integer lendDay;

}
