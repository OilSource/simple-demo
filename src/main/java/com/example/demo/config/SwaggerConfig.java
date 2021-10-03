package com.example.demo.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))//扫描包
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))//扫描在API注解的contorller
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//扫描带ApiOperation注解的方法
                .paths(PathSelectors.any())
                .build();//自定义host
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("simple-demo管理系统")
                .description("方便初始化的项目")
                .version("v2.")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("test", "", "test@onlyedu.com"))
                .build();
    }


}
