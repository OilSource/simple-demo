package com.example.demo.cacheloader;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.config.localcache.LocalCacheLoader;
import com.example.demo.entity.Constant;
import com.example.demo.entity.Menu;
import com.example.demo.enums.LocalCacheType;
import com.example.demo.mapper.ConstantMapper;
import com.example.demo.mapper.MenuMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class MenuCacheLoader implements LocalCacheLoader<Map<Long, Menu>> {

    @Resource
    private MenuMapper menuMapper;


    @Override
    public String category() {
        return LocalCacheType.MENU.value();
    }

    @Override
    public Supplier<Map<Long, Menu>> getValueSupplier() {
        return ()->{
            Map<Long, Menu> constantMap = new HashMap<>();
            List<Menu> constantList = menuMapper.selectList(Wrappers.emptyWrapper());
            constantList.forEach(item ->{
                constantMap.put(item.getId(),item);
            });
            return constantMap;
        };
    }
}
