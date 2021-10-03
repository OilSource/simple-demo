package com.example.demo.config.seurity;

import cn.dev33.satoken.config.SaTokenConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SecurityConfig {

    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("ticket");
        config.setTimeout(1800);
        config.setActivityTimeout(1800);
        config.setIsConcurrent(false);
        config.setIsShare(false);
        config.setIsReadCookie(false);
        config.setIsLog(true);
        return config;
    }
}
