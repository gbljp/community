package com.anjoy.cloud.component.feign.client;


import com.anjoy.cloud.component.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hw77-component-buyer")
public interface TestEurekaServerInterface {

    @RequestMapping(value = "/seller/test", method = RequestMethod.GET)
    public JsonResult hello(@RequestParam("account") String account);
}
