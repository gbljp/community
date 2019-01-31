package com.anjoy.cloud.component.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.anjoy.cloud.component.controller.interceptor.GlobalInterceptor;
import com.anjoy.cloud.component.controller.interceptor.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

    @Value("${microservice.usingFastJsonAsDefaultHttpMessageConverter}")
    Boolean ifUsingFastJson;

    @Value("${microservice.fastjson.localdatetimeConvert}")
    String fastJsonConvert;

    @Bean
    public GlobalInterceptor globalInterceptor(){
        return new GlobalInterceptor();
    }

    @Bean
    public LoggerInterceptor loggerInterceptor(){
        return new LoggerInterceptor();
    }


    /*
    *
    * 全局拦截器，默认全部拦截，列出来的URL不通过拦截器，拦截器内部使用的token校验
    * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //日志拦截注入
        registry.addInterceptor(loggerInterceptor()).addPathPatterns("/**");
        //token验证拦截注入
        registry.addInterceptor(globalInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(
                        //wuhy 这里是swagger的配置，暂时不需要改
                        "/swagger-resources/**",
                        swaggerHome+"/**",
                        swaggerJsonHome,

                        //静态资源目录
//                        "/webser/storage/**",

                        //默认的错误信息
                        "/error",


                        //wuhy 这里添加排除列表，将不需要使用token控制的功能排除在外
                        "/buyer/**",
                        "/buyer/test1/**",
                        "/seller/test");
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
        //静态资源映射
//        registry.addResourceHandler("/webser/storage/**").addResourceLocations("file:/webser/storage/");
    }

    /*
     *
     * 全局替换JSON解析器为阿里巴巴的fastjson解析器
     * */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        if (ifUsingFastJson) {
            //禁用jackson解析器
            Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
            while (iterator.hasNext()) {
                HttpMessageConverter<?> converter = iterator.next();
                if (converter instanceof MappingJackson2HttpMessageConverter) {
                    iterator.remove();
                }
            }

            //添加fastjson解析器
            //1.定义一个消息转换对象convert
            FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

            //2.添加fastJson配置信息，是否需要格式化
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
            fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                    SerializerFeature.QuoteFieldNames,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.UseISO8601DateFormat);

            fastJsonConfig.setSerializeFilters((ValueFilter) (o, s, source) -> {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//                if (source == null) {
//                    return "";
//                }
                if (source instanceof LocalDateTime) {
                    if (fastJsonConvert.equals("timestamp")){
                        return ((LocalDateTime)source).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    }else if (fastJsonConvert.equals("datetime")){
                        return df.format((LocalDateTime)source);
                    }
                }
                return source;
            });

            List<MediaType> fastMediaTypes = new ArrayList<>();
            fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
            fastConverter.setSupportedMediaTypes(fastMediaTypes);

            //3.在convert添加配置信息
            fastConverter.setFastJsonConfig(fastJsonConfig);

            //4.将convert添加到converters中
            converters.add(fastConverter);
        }
    }
}