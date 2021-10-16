package com.example.demo.redis;

import com.example.demo.constants.RedisKeys;
import com.example.demo.utils.RedisUtil;
import com.example.demo.vo.resp.UserInfoResp;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class UserRedis {

    @Resource
    private RedisUtil redisUtil;

    public void setTokenUser(String token,UserInfoResp userInfoResp) {
        redisUtil.setEx(RedisKeys.getUserTokenKeys(token), userInfoResp, 1800, TimeUnit.SECONDS);
    }

    public UserInfoResp getTokenUser(String token) {
        return (UserInfoResp) redisUtil.get(RedisKeys.getUserTokenKeys(token));
    }

    public void deleteTokenUser(String token) {
        redisUtil.delete(RedisKeys.getUserTokenKeys(token));
    }

    public void refreshTokenUser(String token) {
        redisUtil.expire(RedisKeys.getUserTokenKeys(token),1800, TimeUnit.SECONDS);
    }

}
