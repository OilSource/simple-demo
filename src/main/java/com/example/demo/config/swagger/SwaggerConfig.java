package com.example.demo.config.swagger;

import com.example.demo.constants.ComConstant;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

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
                .build().securitySchemes(securitySchemes()).securityContexts(securityContext());//自定义host
    }


    private List securitySchemes() {
        return Collections.singletonList(new ApiKey(ComConstant.AUTHORIZATION_KEY, ComConstant.AUTHORIZATION_KEY, "header"));
    }

    private List securityContext() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
        return Collections.singletonList(securityContext);
    }

    List defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(ComConstant.AUTHORIZATION_KEY, authorizationScopes));
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
