package com.anjoy.cloud.component.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swaggers2 配置
 */
@Configuration
@EnableSwagger2
public class SwaggersConfig {

//	@Bean
//	public Docket createRestApi() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enableUrlTemplating(true).select()
//				// 扫描所有有注解的api，用这种方式更灵活
//				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
//				.build();
//	}
//	/**
//     * 首页描述
//     * @return
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("冻品鲜生为android或者ios客户端提供API请求接口")
//                .description("")
//                .termsOfServiceUrl("")
//                .build();
//    }

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(true)
				.apiInfo(apiInfo())
				.forCodeGeneration(true)
				.useDefaultResponseMessages(false)
				.select()
				// 只过滤包含有ApiOperation注解的方法
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("冻品鲜生-seller模块")
				.description("")
				.termsOfServiceUrl("http://")
				.contact(new Contact("冻品鲜生","",""))
				.version("1.0.0")
				.build();
	}
}