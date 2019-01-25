package com.anjoy.cloud.component.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swaggers2 配置
 */
@Configuration
@EnableSwagger2
public class SwaggersConfig {

	@Value("${microservice.swagger.name}")
	private String swaggerName;

	@Bean
	public Docket api() {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		List<Parameter> parameters = new ArrayList<>();
		parameterBuilder.name("token")
				.description("用户令牌")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false).build();
		parameters.add(parameterBuilder.build());
		parameterBuilder.name("userId")
				.description("用户ID")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false).build();
		parameters.add(parameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.enable(true)
				.apiInfo(apiInfo())
				.forCodeGeneration(true)
				.useDefaultResponseMessages(false)
				.select()
				// 只过滤包含有ApiOperation注解的方法
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(parameters);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("安井微服务开发平台在线API文档 - "+swaggerName)
				.description("")
				.termsOfServiceUrl("http://")
				.contact(new Contact("吴宏宇","",""))
				.version("1.0.0")
				.build();
	}
}