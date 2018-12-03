package com.anjoy.frozen.seller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

/**
 * SpringBoot的启动类
 */
@SpringBootApplication
@MapperScan("com.anjoy.frozen.seller.dao") // mapper层的路径
@ImportResource("classpath:transaction.xml")
@EnableFeignClients    //加入feign的支持
public class AnjoyComponentSampleApplication {

    public static void main(String[] args) {
         SpringApplication.run(AnjoyComponentSampleApplication.class, args);
    }
}