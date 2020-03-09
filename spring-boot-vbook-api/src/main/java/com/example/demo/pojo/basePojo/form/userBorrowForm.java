package com.example.demo.pojo.basePojo.form;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 * <p>
 * 统计某专业各学生的借书次数 form
 */
@Data
public class userBorrowForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 3238548283628564916L;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     *预计毕业年份
     */
    @Size(max = 4, message = "预计毕业年份最长4个字符")
    private Integer userGraduationYear;

    /**
     *开始日期
     */
    @Size(max = 16, message = "开始日期最长16个字符")
    private String start;

    /**
     *结束日期
     */
    @Size(max = 16, message = "结束日期最长16个字符")
    private String end;

    /**
     *借书时长
     */
    @Size(max = 16, message = "借书时长最长16个字符")
    private Integer lendDay;

    /**
     *专业
     */
    @Size(max = 16, message = "专业最长16个字符")
    private String userSpecialities;

}
