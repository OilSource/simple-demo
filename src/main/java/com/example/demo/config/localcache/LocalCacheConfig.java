package com.example.demo.config.localcache;

import cn.hutool.core.collection.CollUtil;
import com.example.demo.constants.ComConstant;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;
@Slf4j
@Configuration
public class LocalCacheConfig {


    public LocalCacheConfig(ObjectProvider<Map<String,LocalCacheLoader>> objectProvider) {
        Map<String, LocalCacheLoader> localCacheLoaderMap = LocalCacheHelper.getLocalCacheLoaderMap();
        Map<String, LocalCacheLoader> loaderMap = objectProvider.getIfAvailable();
        if(CollUtil.isNotEmpty(loaderMap)){
            loaderMap.forEach((k,v)->{
                localCacheLoaderMap.put(v.category(),v);
            });

        }
    }

    @Bean
    public CacheManager localCacheManager(){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheLoader(cacheLoader());
        caffeineCacheManager.setCaffeine(caffeineCacheBuilder());
        caffeineCacheManager.setAllowNullValues(true);
        LocalCacheHelper.setLocalCacheManager(caffeineCacheManager);
        initLocalCache(caffeineCacheManager);
        return caffeineCacheManager;
    }

    private void initLocalCache(CaffeineCacheManager cacheManager){
        log.info("初始化本地缓存localCache");
        Cache cache = cacheManager.getCache(ComConstant.LOCAL_CACHE_NAME);
        Map<String, LocalCacheLoader> localCacheLoaderMap = LocalCacheHelper.getLocalCacheLoaderMap();
        localCacheLoaderMap.forEach((k,v)->{
            cache.get(k);
        });
    }


    private Caffeine<Object,Object> caffeineCacheBuilder(){
        return Caffeine.newBuilder().initialCapacity(100).maximumSize(200000L);
    }

    @Bean
    public CacheLoader<Object,Object> cacheLoader(){
        return category -> {
            Map<String, LocalCacheLoader> localCacheLoaderMap = LocalCacheHelper.getLocalCacheLoaderMap();
            LocalCacheLoader localCacheLoader = localCacheLoaderMap.get(category);
            if(Objects.isNull(localCacheLoader)){
                throw new RuntimeException("category "+ category+"找不到对应的localCacheLoader");
            } else {
                return localCacheLoader.getValueSupplier().get();
            }
        };
    }


}
