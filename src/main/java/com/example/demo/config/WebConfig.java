package com.example.demo.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig  implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 放行哪些原始域
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        // 是否发送cookie信息
        corsConfiguration.setAllowCredentials(false);
        // 放行哪些原始域（请求方式）
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        // 放行哪些原始域（头部信息）
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        corsConfiguration.addExposedHeader(HttpHeaders.ACCEPT);

        // 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);

    }

}
