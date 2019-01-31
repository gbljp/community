package com.anjoy.cloud.component.feign.client;


import com.alibaba.fastjson.JSONObject;
import com.anjoy.cloud.component.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
*
* feignClient - 远程授权服务
* */
@FeignClient(name = "${feignClient.authorizeServiceName}")
public interface AuthorizeFeignClient {

    //请求校验token
    @RequestMapping(value = "authorizeHeader/verifyToken", method = RequestMethod.POST)
    public JsonResult verifyToken();

    //清除token的redis缓存的cache，一般用于重置密码后
    @RequestMapping(value = "/authorizeHeader/clearPasswordCache", method = RequestMethod.POST)
    public JsonResult clearPasswordCache();

    //生成token
    @RequestMapping(value = "/authorize/generateToken", method = RequestMethod.POST)
    public JsonResult generateToken(@RequestBody JSONObject body);
}
