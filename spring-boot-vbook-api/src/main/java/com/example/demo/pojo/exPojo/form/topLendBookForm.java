package com.example.demo.pojo.exPojo.form;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 *
 * 统计借书次数最多的书籍 form
 *
 */
@Data
public class topLendBookForm implements Serializable {
    private static final long serialVersionUID = 8949692645816254661L;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;
    /**
     * 当前页
     */
    private long current = 1;
    //预计毕业年份
    @Size(max = 4, message = "预计毕业年份最长4个字符")
    private Integer userGraduationYear;
    //专业
    @Size(max = 16, message = "预计专业最长16个字符")
    private String userSpecialities;
    //升序降序
    private String isDesc;
}
