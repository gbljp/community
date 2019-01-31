package com.anjoy.cloud.component.controller.exception;

import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DefaultErrorController implements ErrorController{

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public JsonResult error(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (statusCode == 404){
            return new JsonResult(JsonResultCode.FAILURE,"请求不存在",null);
        }else {
            return new JsonResult(JsonResultCode.FAILURE,"请求发生异常，请重试",exception.getMessage());
        }
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}