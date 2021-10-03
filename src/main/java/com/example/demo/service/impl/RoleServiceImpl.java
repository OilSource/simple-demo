package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.common.dto.resp.PageVO;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleMenu;
import com.example.demo.entity.User;
import com.example.demo.enums.StateType;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.service.RoleMenuService;
import com.example.demo.service.RoleService;
import com.example.demo.vo.req.RoleAuthorityReq;
import com.example.demo.vo.req.RoleQuery;
import com.example.demo.vo.req.RoleSaveReq;
import com.example.demo.vo.resp.RolePageResp;
import com.example.demo.vo.resp.UserPageResp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuService roleMenuService;


    @Override
    public ApiResult saveRole(RoleSaveReq roleSaveReq) {
        Role role = new Role();
        BeanUtil.copyProperties(roleSaveReq,role);
        role.setRoleState(StateType.Valid.value());
        baseMapper.insert(role);
        return ApiResult.ok("添加角色成功！",null);
    }

    @Override
    public ApiResult updateRole(RoleSaveReq roleSaveReq) {
        Role role = new Role();
        BeanUtil.copyProperties(roleSaveReq,role);
        baseMapper.updateById(role);
        return ApiResult.ok("更新角色成功！",null);
    }

    @Override
    public ApiResult deleteRoleById(String id) {
        baseMapper.deleteById(id);
        return ApiResult.ok("删除角色角色成功！",null);
    }

    @Override
    public ApiResult pageList(RoleQuery roleQuery) {
        Page<Role> page = new Page<>(roleQuery.getPageNum(), roleQuery.getPageSize());
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotEmpty(roleQuery.getRoleName()), "role_name", roleQuery.getRoleName());
        Page<Role> userPage = baseMapper.selectPage(page, wrapper);
        List<RolePageResp> rolePageResps = BeanUtil.copyToList(userPage.getRecords(), RolePageResp.class);
        return ApiResult.ok("获取角色列表成功", new PageVO<RolePageResp>(userPage.getTotal(), rolePageResps));
    }

    @Override
    public ApiResult listRole() {
        return ApiResult.ok("获取角色列表成功！",baseMapper.selectList(Wrappers.emptyWrapper()));
    }


    @Override
    public ApiResult updateRoleState(RoleSaveReq roleSaveReq) {
        Role role = new Role();
        role.setId(roleSaveReq.getId());
        role.setRoleState(roleSaveReq.getRoleState());
        baseMapper.updateById(role);
        return ApiResult.ok("更新角色状态成功！",null);
    }


    @Override
    @Transactional
    public ApiResult authority(RoleAuthorityReq roleAuthorityReq) {
        if(CollUtil.isNotEmpty(roleAuthorityReq.getMenuIdList())){
            Role role = baseMapper.selectById(roleAuthorityReq.getRoleId());
            if(null == role){
                return ApiResult.fail("角色不存在！");
            }
            QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",roleAuthorityReq.getRoleId());
            List<RoleMenu> list = roleMenuService.list(wrapper);
            List<Long> menuIdList = roleAuthorityReq.getMenuIdList();
            List<Long> deleteIdList = new ArrayList<>();
            for(RoleMenu roleMenu :list){
                boolean flag = true;
                Iterator<Long> iterator = menuIdList.iterator();
                while (iterator.hasNext()){
                    Long next = iterator.next();
                    if(next.equals(roleMenu.getMenuId())){
                        flag = false;
                        iterator.remove();
                        break;
                    }
                }
                if(flag){
                    deleteIdList.add(roleMenu.getMenuId());
                }
            }
            //先删除
            if(CollUtil.isNotEmpty(deleteIdList)){
                roleMenuService.remove(new QueryWrapper<RoleMenu>().in("menu_id",deleteIdList));
            }
            //在插入
            if(CollUtil.isNotEmpty(menuIdList)){
                List<RoleMenu> roleMenuList = new ArrayList<>();
                menuIdList.forEach(menuId ->{
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(roleAuthorityReq.getRoleId());
                    roleMenu.setMenuId(menuId);
                    roleMenuList.add(roleMenu);
                });
                roleMenuService.saveBatch(roleMenuList);
            }
        } else {
            roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleAuthorityReq.getRoleId()));
        }
        return ApiResult.ok("角色授权成功！",null);
    }
}
