package com.example.demo.vo;

import com.example.demo.entity.User;
import com.usthe.sureness.provider.SurenessAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserAccountVO extends User implements SurenessAccount {

    @Getter
    @Setter
    private List<String> roles;

    @Override
    public String getAppId() {
        return getUsername();
    }

    @Override
    public String getPassword() {
        return getPwd();
    }

    @Override
    public String getSalt() {
        return null;
    }

    @Override
    public List<String> getOwnRoles() {
        return roles;
    }

    @Override
    public boolean isDisabledAccount() {
        return 0 == getUserState();
    }

    @Override
    public boolean isExcessiveAttempts() {
        return false;
    }
}
