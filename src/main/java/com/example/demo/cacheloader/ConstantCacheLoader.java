package com.example.demo.cacheloader;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.config.localcache.LocalCacheLoader;
import com.example.demo.entity.Constant;
import com.example.demo.enums.LocalCacheType;
import com.example.demo.mapper.ConstantMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
@Component
public class ConstantCacheLoader implements LocalCacheLoader<Map<Long, Constant>> {

    @Resource
    private ConstantMapper constantMapper;


    @Override
    public String category() {
        return LocalCacheType.CONSTANT.value();
    }

    @Override
    public Supplier<Map<Long, Constant>> getValueSupplier() {
        return ()->{
            Map<Long, Constant> constantMap = new HashMap<>();
            List<Constant> constantList = constantMapper.selectList(Wrappers.emptyWrapper());
            constantList.forEach(item ->{
                constantMap.put(item.getId(),item);
            });
            return constantMap;
        };
    }
}
