package com.example.demo.config.security.provider;

import cn.hutool.core.util.StrUtil;
import com.example.demo.constants.ComConstant;
import com.example.demo.entity.Constant;
import com.example.demo.service.ConstantService;
import com.usthe.sureness.matcher.PathTreeProvider;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class SimplePathTreeProvider implements PathTreeProvider {

    @Resource
    private ConstantService constantService;

    @Override
    public Set<String> providePathData() {
        return null;
    }

    @Override
    public Set<String> provideExcludedResource() {
        Set<String> excludedResource = new HashSet<>();
        Constant constant = constantService.getIgnoreUrl();
        if (null != constant && StrUtil.isNotEmpty(constant.getConstValue())) {
            for (String path : constant.getConstValue().split(ComConstant.COM_SYMBOL)) {
                excludedResource.add(path);
            }
        }
        return excludedResource;
    }
}
