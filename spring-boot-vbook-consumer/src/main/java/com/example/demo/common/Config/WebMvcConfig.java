package com.example.demo.common.Config;

import com.example.demo.common.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by ytl on 2019/10/9.
 * <p>
 * 拦截器 跨域配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 登录认证拦截器
    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/error")                 //springboot 异常error页面
                .excludePathPatterns("/Vbook/register")          //注册
                .excludePathPatterns("/Vbook/login")             //登录
                .excludePathPatterns("/Vbook/outlogin");            //退出登录
    }

}
