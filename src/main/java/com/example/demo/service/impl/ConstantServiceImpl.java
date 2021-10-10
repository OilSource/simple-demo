package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.common.dto.resp.PageVO;
import com.example.demo.config.redis.RedisSender;
import com.example.demo.constants.ConstConstant;
import com.example.demo.entity.Constant;
import com.example.demo.enums.LocalCacheType;
import com.example.demo.enums.RedisMessageType;
import com.example.demo.mapper.ConstantMapper;
import com.example.demo.service.ConstantService;
import com.example.demo.vo.req.ConstantQuery;
import com.example.demo.vo.req.ConstantSaveReq;
import com.example.demo.vo.resp.ConstantPageResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConstantServiceImpl extends ServiceImpl<ConstantMapper, Constant> implements ConstantService {

    @Resource
    private RedisSender redisSender;

    @Override
    public ApiResult pageList(ConstantQuery constantQuery) {
        Page<Constant> page = new Page<>(constantQuery.getPageNum(), constantQuery.getPageSize());
        QueryWrapper<Constant> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotEmpty(constantQuery.getConstKey()), "const_key", constantQuery.getConstKey());
        Page<Constant> constantPage = baseMapper.selectPage(page, wrapper);
        List<ConstantPageResp> constantPageResps = BeanUtil.copyToList(constantPage.getRecords(), ConstantPageResp.class);
        return ApiResult.ok("获取常量列表成功", new PageVO<ConstantPageResp>(constantPage.getTotal(), constantPageResps));
    }

    @Override
    public ApiResult saveConstant(ConstantSaveReq constantSaveReq) throws JsonProcessingException {
        Constant constant = new Constant();
        BeanUtil.copyProperties(constantSaveReq, constant);
        constant.setId(null);
        baseMapper.insert(constant);
        redisSender.send(RedisMessageType.LOCAL_CACHE.num(), LocalCacheType.CONSTANT.value());
        return ApiResult.ok("添加常量成功！");
    }

    @Override
    public Constant getIgnoreUrl() {
        QueryWrapper<Constant> wrapper = new QueryWrapper<>();
        wrapper.eq("const_key", ConstConstant.IGNORE_URL_KEY);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public ApiResult deleteConstantById(Long id) throws JsonProcessingException {
        baseMapper.deleteById(id);
        redisSender.send(RedisMessageType.LOCAL_CACHE.num(), LocalCacheType.CONSTANT.value());
        return ApiResult.ok("删除常量成功！");
    }

    @Override
    public ApiResult updateConstantStatus(ConstantSaveReq constantSaveReq) throws JsonProcessingException {
        Constant constant = new Constant();
        constant.setId(constantSaveReq.getId());
        constant.setConstStatus(constantSaveReq.getConstStatus());
        baseMapper.updateById(constant);
        redisSender.send(RedisMessageType.LOCAL_CACHE.num(), LocalCacheType.CONSTANT.value());
        return ApiResult.ok("更新常量状态成功！", null);
    }


    @Override
    public ApiResult updateConstant(ConstantSaveReq constantSaveReq) throws JsonProcessingException {
        Constant constant = new Constant();
        constant.setId(constantSaveReq.getId());
        constant.setConstKey(constantSaveReq.getConstKey());
        constant.setConstValue(constantSaveReq.getConstValue());
        constant.setRemark(constantSaveReq.getRemark());
        baseMapper.updateById(constant);
        redisSender.send(RedisMessageType.LOCAL_CACHE.num(), LocalCacheType.CONSTANT.value());
        return ApiResult.ok("更新常量成功！");
    }
}
