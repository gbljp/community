package com.anjoy.cloud.component.feign.client;

/*
 *
 * feign 的调用对象接口配置
 * 说明：feign是一种适合java调用restful接口的一种声明式的调用方式，本质上是将需要调用的restful接口映射
 * 成本地可以直接访问的一个interface，并使用spring注入的技术在项目启动时装配并注入到需要使用的对象中去
 * 这样可以在一个项目中使用同一套配置在不同的区域中进行调用
 * 详细的配置请百度
 *
 * */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${feignClient.testClient}", name = "testClient")
public interface TestFeignInterface {
    @RequestMapping(value = "/oss/common/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("name") String name);
}