package com.example.demo.constants;

public class RedisKeys {

    public static final String COMMON_MESSAGE_TOPIC = "commonTopic";



    public static String getUserTokenKeys(String token){
        return "user:token:"+token;
    }
}
