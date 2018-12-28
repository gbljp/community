package com.anjoy.cloud.component.config;

import com.anjoy.cloud.component.controller.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * 增加全局拦截器
 *
 * @author Administrator
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurationSupport {


    /*
    *
    * 全局拦截器，默认全部拦截，列出来的URL不通过拦截器，拦截器内部使用的token校验
    * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(
                        //wuhy 这里是swagger的配置，暂时不需要改
                        "/swagger-resources/**",
                        "/anjoyCloudApiDoc/**",
                        "/anjoyCloudApiDocJson",


                        //wuhy 这里添加排除列表，将不需要使用token控制的功能排除在外
                        "/buyer/test",
                        "/buyer/test1/**");
        super.addInterceptors(registry);
    }

    /*
    *
    * 全局视图映射
    * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addRedirectViewController("/anjoyCloudApiDoc/anjoyCloudApiDocJson", "/anjoyCloudApiDocJson");
        registry.addRedirectViewController("/anjoyCloudApiDoc/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/anjoyCloudApiDoc/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/anjoyCloudApiDoc/swagger-resources", "/swagger-resources");
    }


    /*
    *
    * 全局静态资源文件映射
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/anjoyCloudApiDoc/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.
                addResourceHandler("/anjoyCloudApiDoc/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
} 