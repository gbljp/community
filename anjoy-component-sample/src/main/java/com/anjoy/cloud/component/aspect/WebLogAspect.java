package com.anjoy.cloud.component.aspect;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;


/*
 *
 * wuhy
 * 全局切面，在项目中主要用于捕获从controller返回出去的内容并记录
 * 注意传统的Interceptor是无法捕获controller返回出去的内容的，必须使用pointcut的方式
 *
 * */
@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    //默认进入controller的切面
    @Pointcut("execution(public * com.anjoy.cloud.component.controller.module.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        // wuhy 这里不需要再记录了，因为LoggerInterceptor已经记录过了
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        // 记录下请求内容
//        logger.info("URL : " + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + request.getRemoteAddr());
//        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint,Object ret) throws Throwable {
        // 接收到请求，记录请求内容
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                if (request != null) {
                    // 记录下请求内容
                    String reqId =request.getHeader("X-Request-Id");
                    sb.append("reqId: ").append(reqId).append("\n");
//                    sb.append("URL: " + request.getRequestURL().toString() + "\n");
                }
            }
            sb.append("Class_Method: "+joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()+"\n");
            sb.append("Args: " + Arrays.toString(joinPoint.getArgs())+"\n");
            try {
                if (ret instanceof String){
                    sb.append("Response: " + ret+"\n");
                }else if (ret instanceof Serializable) {
                    sb.append("Response: " + JSON.toJSONString(ret) + "\n");
                }else{
                    sb.append("Response: " + ret+"\n");
                }
            } catch (Exception e) {
                sb.append("Response: " + ret+"\n");
            }
            logger.debug(sb.toString());
        } catch (Exception e) {
            logger.error("WebLogAspect切面获取返回值出错",e);
        }
    }

}