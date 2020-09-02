package com.weekly.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${debug}")
    private Boolean debug;

    @Bean
    public Docket getApiBean() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("demo")
            .apiInfo(apiInfo())
            .enable(true)
            .enable(Boolean.TRUE.equals(debug))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.yhc.demo.controller"))
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("API 文档")
            .version("1.0")
            .description("API 描述")
            .build();
    }
}
