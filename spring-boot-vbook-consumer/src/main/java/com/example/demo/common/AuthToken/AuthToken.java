package com.example.demo.common.AuthToken;

import java.lang.annotation.*;

/**
 * Created by ytl on 2019/10/9.
 * <p>
 * 身份验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthToken {

    /**
     * 访问所需的身份
     *
     * @return
     */
    int role_name() default 0;

}
