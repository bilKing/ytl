package com.example.demo.pojo.exPojo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Administrator on 2019/10/29.
 *
 * 添加公告form
 *
 */
@Data
public class noticeForm implements Serializable {
    private static final long serialVersionUID = 7093516725103647395L;
    //公告
    @Size(max = 100, message = "公告最长100个字符")
    @NotNull(message = "公告不能为空")
    private String notice;
}
