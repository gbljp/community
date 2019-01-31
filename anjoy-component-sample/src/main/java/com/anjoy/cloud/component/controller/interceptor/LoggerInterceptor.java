package com.anjoy.cloud.component.controller.interceptor;

import com.anjoy.cloud.component.controller.filter.BodyReaderHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/*
 * 日志拦截注入
 * wuhy
 *
 * */
public class LoggerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String url = request.getContextPath()+request.getRequestURI();
        //如果是swagger页面刷新请求则忽略记录日志
        if (url.startsWith("/swagger-resources")) return true;
        //获取登陆时候传入的token  wuhy20181129
        String token= request.getHeader("token");
        String userId= request.getHeader("userId");
        String reqId =request.getHeader("X-Request-Id");

        try {
            if(handler instanceof HandlerMethod){
                StringBuilder sb = new StringBuilder();
                sb.append("\n");
                sb.append("URL: ").append(request.getRequestURL()).append("\n");
                sb.append("ContextPathAndURI: ").append(url).append("\n");
                sb.append("Controller: ").append(((HandlerMethod) handler).getBean().getClass().getName()).append("\n");
                sb.append("Method: ").append(((HandlerMethod) handler).getMethod().getName()).append("\n");
                sb.append("token: ").append(token).append("\n");
                sb.append("userId: ").append(userId).append("\n");
                sb.append("reqId: ").append(reqId).append("\n");
                //通过输入流获取POST请求中的参数
                sb.append("Body: ").append(new BodyReaderHttpServletRequestWrapper(request).getBodyString()).append("\n");
                sb.append("ParamsMap: ").append(getParamString(request.getParameterMap())).append("\n");
                logger.debug(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,   ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }
}
