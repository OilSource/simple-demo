package com.example.demo.config.redis;

import cn.hutool.core.util.StrUtil;
import com.example.demo.constants.ComConstant;
import com.example.demo.enums.RedisMessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;


@Slf4j
public class RedisListener implements MessageListener {

    private RedisTemplate<String,Object> redisTemplate;

    private ObjectMapper objectMapper;

    private CacheManager cacheManager;

    public RedisListener(RedisTemplate<String, Object> redisTemplate,CacheManager cacheManager) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
        this.cacheManager = cacheManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisOperation redisOperation = (RedisOperation)redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.info("接手redis订阅发布消息, type:{} messageData:{}",redisOperation.getType(),redisOperation.getData());
        RedisMessageType redisMessageType = RedisMessageType.getByNum(redisOperation.getType());
        switch (redisMessageType){
            case LOCAL_CACHE:
                String key = redisOperation.getData();
                Cache cache = cacheManager.getCache(ComConstant.LOCAL_CACHE_NAME);
                if(Objects.nonNull(cache)){
                    if(StrUtil.isEmpty(key)){
                        log.warn("刷新本地缓存传的key为空!");
                    } else {
                        cache.evict(key);
                        cache.get(key);
                        log.info("重新加载本地缓存,category:{}",key);
                    }
                } else {
                    log.warn("本地缓存不存在，key:{} ",ComConstant.LOCAL_CACHE_NAME);
                }
                break;
        }
    }
}
