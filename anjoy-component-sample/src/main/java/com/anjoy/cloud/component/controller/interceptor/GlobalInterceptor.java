package com.anjoy.cloud.component.controller.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.anjoy.cloud.component.exception.ServiceException;
import com.anjoy.cloud.component.feign.client.AuthorizeFeignClient;
import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import com.anjoy.cloud.component.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */  
@Controller
public class GlobalInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Autowired
    AuthorizeFeignClient authorizeFeignClient;

    //是否启用远端权限验证服务
    @Value("${microservice.useRemoteAuthorizeService}")
    boolean isUseRemoteAuthorizeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String url = request.getContextPath()+request.getRequestURI();

        //获取登陆时候传入的token  wuhy20181129
        String token= request.getHeader("token");
        String userId= request.getHeader("userId");

        logger.debug("GlobalInterceptor.preHandle.url= " +url+",token:"+token+",userId:"+userId);

        //如果没有token则返回需要重新登陆的提示  wuhy20181129
        if(StringUtils.isBlank(token))
        {
            logger.debug("[Token is null]："+url);
            throw new ServiceException(JsonResultCode.NEEDLOGIN,"您还未登陆或登陆已过期，请重新登陆");
        }

        if(StringUtils.isBlank(userId))
        {
            logger.debug("[userId is null]："+url);
            throw new ServiceException(JsonResultCode.FAILURE,"未传入用户，请检查调用方法传入参数");
        }

        if (isUseRemoteAuthorizeService) {
            JsonResult jr = authorizeFeignClient.verifyToken();
            //如果返回值不是SUCCESS，抛出对应的错误信息
            if (!jr.getCode().equals(JsonResultCode.SUCCESS)) {
                if (jr.getObject() != null) {
                    logger.error(jr.getObject().toString());
                }
                throw new ServiceException(jr.getCode(), jr.getMessage());
            }
        }else{
            //本地验证，兼容旧版token
            if (!userId.equals(TokenService.verifyToken(token))){
                logger.info("[userId NOT EQUAL token]："+url);
                throw new ServiceException(JsonResultCode.NEEDLOGIN,"用户与令牌不匹配，请重新登陆");
            }
        }

        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,   ModelAndView modelAndView) throws Exception {
     //判断错误码
        if(response.getStatus()==500)
        {
            response.sendRedirect("/error/index");//重定向

        }else if(response.getStatus()==404){  
             response.sendRedirect("/error/index");//重定向
        }  
    }  
    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
    }



}