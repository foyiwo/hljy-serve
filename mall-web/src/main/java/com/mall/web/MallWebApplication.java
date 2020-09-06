package com.mall.web;


import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//入口
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, PageHelperAutoConfiguration.class})
@SpringBootApplication
public class MallWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallWebApplication.class, args);
    }
}
