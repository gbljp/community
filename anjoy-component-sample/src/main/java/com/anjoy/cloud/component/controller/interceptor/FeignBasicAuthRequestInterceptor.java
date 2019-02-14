package com.anjoy.cloud.component.controller.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/*
*
* feign 请求拦截器，用于转发当前请求的header实现鉴权共享
* wuhy
*
* */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes != null) {   //异步调用feign的时候，是获取不到原线程中的请求头信息的，这边拿到的就是null，所以异步调用需要自己处理请求头
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
        }
    }
}