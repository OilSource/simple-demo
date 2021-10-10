package com.example.demo.config.localcache;

import com.example.demo.constants.ComConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LocalCacheHelper {

    private static  CacheManager localCacheManager;

    private static Map<String,LocalCacheLoader> localCacheLoaderMap = new ConcurrentHashMap<>();

    public static<T>T getLocalData(String cacheName,String key){
        Cache cache = localCacheManager.getCache(cacheName);
        if(Objects.isNull(cache)){
            return null;
        } else {
            return (T) cache.get(key).get();
        }

    }
    public static<T>T getLocalData(String key){
        return getLocalData(ComConstant.LOCAL_CACHE_NAME,key);

    }

    public static Map<String, LocalCacheLoader> getLocalCacheLoaderMap() {
        return localCacheLoaderMap;
    }

    public static void setLocalCacheLoaderMap(Map<String, LocalCacheLoader> localCacheLoaderMap) {
        LocalCacheHelper.localCacheLoaderMap = localCacheLoaderMap;
    }

    public static void setLocalCacheManager(CacheManager localCacheManager) {
        LocalCacheHelper.localCacheManager = localCacheManager;
    }
}
