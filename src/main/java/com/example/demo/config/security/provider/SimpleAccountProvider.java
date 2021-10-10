package com.example.demo.config.security.provider;

import com.example.demo.service.UserService;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SimpleAccountProvider implements SurenessAccountProvider {

    @Resource
    private UserService userService;


    @Override
    public SurenessAccount loadAccount(String s) {
        return null;
    }
}
