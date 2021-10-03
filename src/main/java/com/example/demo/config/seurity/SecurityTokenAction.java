package com.example.demo.config.seurity;

import cn.dev33.satoken.action.SaTokenActionDefaultImpl;
import cn.dev33.satoken.util.SaFoxUtil;
import org.springframework.stereotype.Component;

@Component
public class SecurityTokenAction extends SaTokenActionDefaultImpl {

    @Override
    public String createToken(Object loginId, String loginType) {
        return SaFoxUtil.getRandomString(60);
    }
}
