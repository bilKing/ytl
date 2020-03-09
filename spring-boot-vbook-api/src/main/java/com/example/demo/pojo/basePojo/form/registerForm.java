package com.example.demo.pojo.basePojo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 注册 form
 */
@Data
public class registerForm implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -9106926645722028933L;

    /**
     * 姓名
     */
    @Size(max = 16, message = "姓名最长16个字符")
    private String userName;

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

    /**
     * 班级
     */
    @Size(max = 30, message = "班级最长30个字符")
    private String userClass;

    /**
     * 专业
     */
    @Size(max = 30, message = "专业最长30个字符")
    private String userSpecialities;

    /**
     * 预计毕业年份
     */
    @Size(max = 4, message = "预计毕业年份最长4个字符")
    private Integer userGraduationYear;

}
