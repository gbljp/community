package com.anjoy.cloud.component.controller.exception;

import com.anjoy.cloud.component.exception.ServiceException;
import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public JsonResult errorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("[GlobalExceptionHandler][errorHandler]exception", e);
        return convertErrorMsg(e);
    }

    private JsonResult convertErrorMsg(Exception e) {
        JsonResult jsonResult = new JsonResult();
        //判断exception类型，如果已经输出的是serviceException则直接抛出，否则转义成用户能看懂的错误输出
        if (e instanceof ServiceException) {
            if (null != ((ServiceException) e).getCode()) {
                jsonResult.setCode(((ServiceException) e).getCode());
            } else {
                jsonResult.setCode(JsonResultCode.FAILURE);
            }
            jsonResult.setMessage(((ServiceException) e).getMessage());
            jsonResult.setObject(null);
        } else if (e instanceof NoHandlerFoundException) {
            jsonResult.setCode(JsonResultCode.FAILURE);
            jsonResult.setMessage("请求不存在");
            jsonResult.setObject(null);
        } else {
            jsonResult.setCode(JsonResultCode.FAILURE);
            jsonResult.setMessage("请求发生异常，请重试");
            jsonResult.setObject(ExceptionUtils.getStackTrace(e));
        }
        return jsonResult;
    }

}