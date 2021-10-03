package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.entity.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.service.MenuService;
import com.example.demo.utils.MenuTreeUtils;
import com.example.demo.vo.MenuNodeVO;
import com.example.demo.vo.req.MenuSaveReq;
import com.example.demo.vo.resp.RoleTreeResp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public ApiResult saveMenu(MenuSaveReq menuSaveReq) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuSaveReq,menu);
        menu.setId(null);
        baseMapper.insert(menu);
        return ApiResult.ok("菜单添加成功！");
    }

    @Override
    public ApiResult tree() {
        List<Menu> menus = baseMapper.selectList(Wrappers.emptyWrapper());
        List<MenuNodeVO> menuNodeVOS = BeanUtil.copyToList(menus, MenuNodeVO.class);
        List<MenuNodeVO> list = MenuTreeUtils.build(menuNodeVOS);
        return ApiResult.ok("获取菜单树成功",list);
    }

    @Override
    @Transactional
    public ApiResult updateMenu(MenuSaveReq menuSaveReq) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuSaveReq,menu);
        baseMapper.updateById(menu);
        return  ApiResult.ok("菜单更新成功！");
    }

    @Override
    public ApiResult deleteByMenuId(String id) {
        baseMapper.deleteById(id);
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
