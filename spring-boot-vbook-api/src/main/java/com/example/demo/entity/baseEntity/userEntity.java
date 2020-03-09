package com.example.demo.entity.baseEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * 用户表 主体pojo
 */
@Data
@TableName("sys_user")
public class userEntity implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -4504233369303851241L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     *权限
     */
    private Integer userAuthority;

    /**
     *班级
     */
    private String userClass;

    /**
     *专业
     */
    private String userSpecialities;

    /**
     *预计毕业年份
     */
    private Integer userGraduationYear;

    /**
     *禁用启用
     */
    private Integer state;

    /**
     *禁用时间
     */
    private String forbiddenTime;

}
