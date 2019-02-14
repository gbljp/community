package com.anjoy.cloud.component.controller.module;


import com.alibaba.fastjson.JSON;
import com.anjoy.cloud.component.controller.base.BaseController;
import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import com.anjoy.cloud.component.token.TokenService;
import com.anjoy.cloud.component.vo.TestVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 卖家信息表.(俗称经销商) 前端控制器
 * </p>
 *
 * @author 
 * @since 2018-08-14
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 测试接口
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "测试输入输出")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public JsonResult test(HttpServletRequest request, HttpServletResponse response, @ApiParam("TestVo")@RequestBody TestVo testVo)throws Exception {
        logger.info("输入的vo信息："+JSON.toJSONString(testVo));
        return new JsonResult(JsonResultCode.SUCCESS, "调用成功", testVo);
    }

    /**
     * 生成token
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "测试生成token")
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public JsonResult getToken(HttpServletRequest request, HttpServletResponse response, @ApiParam("TestVo")@RequestBody TestVo testVo)throws Exception {
        logger.info("输入的vo信息："+JSON.toJSONString(testVo));
        String token = TokenService.generateToken(TokenService.newApiToken(testVo.getAccount(),testVo.getId(),null));
        return new JsonResult(JsonResultCode.SUCCESS, "生成token成功", token);
    }

    /**
     * 校验token
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "测试校验token")
    @RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
    public JsonResult verifyToken(HttpServletRequest request, HttpServletResponse response)throws Exception {
        return new JsonResult(JsonResultCode.SUCCESS, "校验token成功",null);
    }



}

