package com.example.demo.cacheloader;

import com.example.demo.config.localcache.LocalCacheHelper;
import com.example.demo.entity.Constant;
import com.example.demo.entity.Menu;
import com.example.demo.enums.LocalCacheType;

import java.util.Map;

public class LocalCacheManager {

    private static String ignoreUrl;

    public static Map<Long, Constant> getAllConstant(){
        return LocalCacheHelper.getLocalData(LocalCacheType.CONSTANT.value());
    }

    public static Map<Long, Menu> getAllMenu(){
        return LocalCacheHelper.getLocalData(LocalCacheType.MENU.value());
    }
}
