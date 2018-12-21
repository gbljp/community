package com.anjoy.cloud.component.controller.exception;

import javax.servlet.http.HttpServletRequest;

import com.anjoy.cloud.component.exception.ServiceException;
import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常处理，比如：404,500
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult errorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("[GlobalExceptionHandler][errorHandler]exception", e);
        JsonResult jsonResult = new JsonResult();
        //判断exception类型，如果已经输出的是serviceException则直接抛出，否则转义成用户能看懂的错误输出
        if (!(e instanceof ServiceException)) {
            //转义exception
            jsonResult.setCode(JsonResultCode.FAILURE);
            jsonResult.setMessage("请求不存在或者异常，请重试");
            jsonResult.setObject(ExceptionUtils.getStackTrace(e));

        } else {
            //直接输出exception
            if (null != ((ServiceException) e).getCode()) {
                jsonResult.setCode(((ServiceException) e).getCode());
            } else {
                jsonResult.setCode(JsonResultCode.FAILURE);
            }
            jsonResult.setMessage(((ServiceException) e).getMessage());
            jsonResult.setObject(null);
        }
        return jsonResult;
    }
}