package com.example.demo.enums;

public enum RedisMessageType {

    LOCAL_CACHE(1),;

    RedisMessageType(int num) {
        this.num = num;
    }

    private int num;

    public int num(){
        return num;
    }

    public static RedisMessageType getByNum(int num){
        for(RedisMessageType redisMessageType:RedisMessageType.values()){
            if(redisMessageType.num == num){
                return redisMessageType;
            }
        }
        return null;
    }
}
