package com.example.demo.cacheloader;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.config.localcache.LocalCacheLoader;
import com.example.demo.constants.ConstConstant;
import com.example.demo.entity.Constant;
import com.example.demo.enums.LocalCacheType;
import com.example.demo.mapper.ConstantMapper;
import com.usthe.sureness.matcher.TreePathRoleMatcher;
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

    @Resource
    private TreePathRoleMatcher treePathRoleMatcher;

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
                if(item.getConstKey().equals(ConstConstant.IGNORE_URL_KEY) && StrUtil.isNotEmpty(item.getConstValue())
                        && !item.getConstValue().equals(ConstConstant.IGNORE_URL_VALUE)){
                    ConstConstant.IGNORE_URL_VALUE = item.getConstValue();
                    treePathRoleMatcher.rebuildTree();
                }
                constantMap.put(item.getId(),item);
            });
            return constantMap;
        };
    }
}
