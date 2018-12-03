package com.anjoy.cloud.component.config;

import com.anjoy.cloud.component.controller.interceptor.ErrorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 增加全局拦截器
 * @author Administrator
 */
@Configuration
@SuppressWarnings("deprecation")
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
	
	
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ErrorInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/component/login",
        		"/error",
        		"/swagger-resources/**",
        		"/v2/**");
        super.addInterceptors(registry);  
    }  
} 