package com.example.demo.common.Config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ytl on 2019/10/8.
 * <p>
 * MyBaits配置
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.example.demo.module.**.mapper")
public class MyBatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        //page.setOverflow(true);
        page.setDialectType("mysql");
        return page;
    }

}
