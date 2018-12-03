package com.anjoy.cloud.component.controller.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/error")
public class ErrorController {

    private  static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = "/index" ,method = {RequestMethod.GET,RequestMethod.POST})
    public JsonResult index(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
    {
        try
        {
            //参数返回
            return new JsonResult(JsonResultCode.FAILURE,"请求不存在或者异常，请重试","");
        }catch (Exception e){
            logger.error("[ErrorController][index]Exception系统异常",e);
            return new JsonResult(JsonResultCode.FAILURE,"系统异常，请稍后重试","");
        }
    }
}