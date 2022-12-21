package com.example.my_project.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.Collections;

public class CodeGenerator {
    public static void main (String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/userInformation", "root", "123456")
                //全局配置
                .globalConfig(builder -> {
                    builder.author("GongYue") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/gongyue1/IdeaProjects/My_Project/src/main/java")// 指定输出目录
                            .dateType(DateType.ONLY_DATE);
                })
                //包名配置
                .packageConfig(builder -> {
                    builder.parent("com.example") // 设置父包名
                            .moduleName("my_project") // 设置父包模块名
                            .entity("entity")
                            .mapper("dao")
                            .service("service")
                            .controller("controller")
                            .xml("dao");
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("seckillGoods"); // 设置需要生成的表名
                            //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
