package com.anjoy.cloud.component.controller.module;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anjoy.cloud.component.controller.base.BaseController;
import com.anjoy.cloud.component.feign.client.TestFeignInterface;
import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import com.anjoy.cloud.component.service.SellerService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 卖家信息表.(俗称经销商) 前端控制器
 * </p>
 *
 * @author 
 * @since 2018-08-14
 */
@RestController
@RequestMapping("/seller")
public class SellerController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    SellerService sellerService;

    @Autowired
    TestFeignInterface testFeignInterface;

    /**
     * 测试接口
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "测试接口", tags = "测试接口", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", paramType = "query", dataType = "String", required = true)
    })
    @RequestMapping(value = "/test", method = { RequestMethod.GET })
    public JsonResult test(HttpServletRequest request, HttpServletResponse response) {
        String account = this.getNotNull("account", request);

//        feign调用实例
        String ls = testFeignInterface.hello(account);

//        List<Seller> ls = sellerService.selectList(new EntityWrapper<Seller>()
//                .eq("seller_name", "必火酒水饮料批发")
//                .or()
//                .eq("seller_name", "清真牛羊肉"));


        logger.info("SellerController.test.account:" + account);
        return new JsonResult(JsonResultCode.SUCCESS, account, ls);
    }

    /**
     * 测试接口json
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "测试接口json", tags = "测试接口json", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号json", paramType = "query", dataType = "String", required = true)
    })
    @RequestMapping(value = "/testjson", method = { RequestMethod.GET })
    public JsonResult testjson(HttpServletRequest request, HttpServletResponse response) {
        String account = this.getNotNull("account", request);

        JSONObject jo = JSON.parseObject(account);

        logger.info("SellerController.test.account:" + jo.toJSONString());
        return new JsonResult(JsonResultCode.SUCCESS, jo.toJSONString(), jo);
    }

}

