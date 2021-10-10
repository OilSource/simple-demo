package com.example.demo.config.redis;

import com.example.demo.constants.RedisKeys;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RedisSender {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    public void send (Integer type,Object obj) throws JsonProcessingException {
        String s = obj instanceof String?(String) obj:objectMapper.writeValueAsString(obj);
        redisTemplate.convertAndSend(RedisKeys.COMMON_MESSAGE_TOPIC,new RedisOperation(type,s));
        log.info("发布redis消息 type:{} value:{}",type,s);
    }
}
