package com.anjoy.cloud.component.config;

import com.anjoy.cloud.component.controller.interceptor.GlobalInterceptor;
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
        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**")
        .excludePathPatterns(
                //wuhy 这里是swagger的配置，暂时不需要
                "/component/login",
        		"/error",
        		"/swagger-resources/**",
        		"/v2/**",
                "/swagger-ui.html",
                "/webjars/springfox-swagger-ui/**",


                //wuhy 这里添加排除列表，将不需要使用token控制的功能排除在外
                "/buyer/test",
                "/buyer/test1/**");
        super.addInterceptors(registry);  
    }  
} 