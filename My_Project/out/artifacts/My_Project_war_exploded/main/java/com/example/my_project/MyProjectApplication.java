package com.example.my_project;

import com.example.my_project.service.IUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MyProjectApplication{
    public static void main(String[] args) {
        SpringApplication.run(MyProjectApplication.class, args);
    }

}