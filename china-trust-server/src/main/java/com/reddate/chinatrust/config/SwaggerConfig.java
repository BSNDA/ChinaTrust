package com.reddate.chinatrust.config;

import org.springframework.context.annotation.Bean;
import  org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import  springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import  springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
// @ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class SwaggerConfig {
     @Bean
     public Docket createRestApi() {
          return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .select()
                   .apis(RequestHandlerSelectors.basePackage("com.reddate.chinatrust.controller")).paths(PathSelectors.any())
                   .build();
     }
     private ApiInfo apiInfo() {
          return new ApiInfoBuilder()
                   .title("China Trust API")
                   .contact(new Contact("danny",  "http://127.0.0.1:8081/swagger-ui.html",  "285597856@qq.com"))
                   .version("1.0")
                   .description("China Trust API Description")
                   .build();
     }
}