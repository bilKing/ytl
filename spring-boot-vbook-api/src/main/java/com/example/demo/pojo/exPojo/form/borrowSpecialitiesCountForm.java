package com.example.demo.pojo.exPojo.form;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/15.
 *
 * 统计各专业借书次数 form
 *
 */
@Data
public class borrowSpecialitiesCountForm implements Serializable {
    private static final long serialVersionUID = -5894257805341404843L;
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
    //开始日期
    @Size(max = 16, message = "开始日期最长16个字符")
    private String start;
    //结束日期
    @Size(max = 16, message = "借书时长最长4个字符")
    private String end;
    //借书时长
    @Size(max = 4, message = "借书时长最长4个字符")
    private Integer lendDay;
}
