package com.anjoy.cloud.component.config;

import com.anjoy.cloud.component.controller.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * 增加全局拦截器
 *
 * @author Administrator
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Value("${microservice.swagger.home}")
    String swaggerHome;

    @Value("${springfox.documentation.swagger.v2.path}")
    String swaggerJsonHome;


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
                        swaggerHome+"/**",
                        swaggerJsonHome,


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
//        wuhy 注意这里的重定向只对开发环境有用，正式环境会用nginx的proxy_pass来做，因为这种重定向会丢失https协议
        registry.addRedirectViewController(swaggerHome+swaggerJsonHome, swaggerJsonHome);
        registry.addRedirectViewController(swaggerHome+"/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController(swaggerHome+"/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController(swaggerHome+"/swagger-resources", "/swagger-resources");
    }


    /*
    *
    * 全局静态资源文件映射
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler(swaggerHome+"/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.
                addResourceHandler(swaggerHome+"/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
} 