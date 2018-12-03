package com.anjoy.cloud.component;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

/**
 * SpringBoot的启动类
 */
@SpringBootApplication
@MapperScan("com.anjoy.cloud.component.dao") // mapper层的路径
@ImportResource("classpath:transaction.xml")
@EnableFeignClients    //加入feign的支持
public class AnjoyComponentSampleApplication {

    public static void main(String[] args) {
         SpringApplication.run(AnjoyComponentSampleApplication.class, args);
    }
}