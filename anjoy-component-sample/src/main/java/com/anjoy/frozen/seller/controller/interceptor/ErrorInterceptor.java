package com.anjoy.frozen.seller.controller.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 自定义拦截器
 */  
@Controller
public class ErrorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {  
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