package com.example.demo.pojo.basePojo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/12.
 * <p>
 * 登录 form
 */
@Data
public class loginForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -4838553368134355734L;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String userPassword;

    /**
     * 学号
     */
    @NotNull(message = "学号不能为空")
    @Size(max = 30, message = "学号最长30个字符")
    private String stuId;

}
