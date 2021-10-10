package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.cacheloader.LocalCacheManager;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.config.redis.RedisSender;
import com.example.demo.entity.Menu;
import com.example.demo.enums.LocalCacheType;
import com.example.demo.enums.RedisMessageType;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.service.MenuService;
import com.example.demo.utils.MenuTreeUtils;
import com.example.demo.vo.MenuNodeVO;
import com.example.demo.vo.req.MenuSaveReq;
import com.example.demo.vo.resp.RoleTreeResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private RedisSender redisSender;

    @Override
    public ApiResult saveMenu(MenuSaveReq menuSaveReq) throws JsonProcessingException {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuSaveReq,menu);
        menu.setId(null);
        baseMapper.insert(menu);
        redisSender.send(RedisMessageType.LOCAL_CACHE.num(), LocalCacheType.MENU.value());
        return ApiResult.ok("菜单添加成功！");
    }

    @Override
    public ApiResult tree() {
        Map<Long, Menu> allMenu = LocalCacheManager.getAllMenu();
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();
        if(CollUtil.isNotEmpty(allMenu)){
            for (Menu menu:allMenu.values()){
                MenuNodeVO menuNodeVO = new MenuNodeVO();
                BeanUtil.copyProperties(menu,menuNodeVO);
                menuNodeVOS.add(menuNodeVO);
            }
        }
        List<MenuNodeVO> list = MenuTreeUtils.build(menuNodeVOS);
        return ApiResult.ok("获取菜单树成功",list);
    }

    @Override
    @Transactional
    public ApiResult updateMenu(MenuSaveReq menuSaveReq) throws JsonProcessingException {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuSaveReq,menu);
        baseMapper.updateById(menu);
        redisSender.send(RedisMessageType.LOCAL_CACHE.num(), LocalCacheType.MENU.value());
        return  ApiResult.ok("菜单更新成功！");
    }

    @Override
    public ApiResult deleteByMenuId(String id) throws JsonProcessingException {
        baseMapper.deleteById(id);
        redisSender.send(RedisMessageType.LOCAL_CACHE.num(), LocalCacheType.MENU.value());
        return ApiResult.ok("菜单删除成功！");
    }


    @Override
    public ApiResult roleTree(Long roleId) {
        RoleTreeResp roleTreeResp = new RoleTreeResp();
        ApiResult apiResult = tree();
        List<Long> menuIdList = baseMapper.getMenuIdListByRoleId(roleId);
        roleTreeResp.setTree((List<MenuNodeVO>)apiResult.getData());
        roleTreeResp.setRoleId(roleId);
        roleTreeResp.setMenuIdList(menuIdList);
        return ApiResult.ok("获取角色权限树成功！",roleTreeResp);
    }
}
