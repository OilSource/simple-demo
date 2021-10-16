package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.cacheloader.LocalCacheManager;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.common.dto.resp.ISecurityUser;
import com.example.demo.common.dto.resp.PageVO;
import com.example.demo.constants.ComConstant;
import com.example.demo.entity.*;
import com.example.demo.enums.StateType;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.redis.UserRedis;
import com.example.demo.service.UserService;
import com.example.demo.utils.EncryptUtils;
import com.example.demo.utils.MenuTreeUtils;
import com.example.demo.vo.MenuNodeVO;
import com.example.demo.vo.req.UserLoginReq;
import com.example.demo.vo.req.UserQuery;
import com.example.demo.vo.req.UserSaveReq;
import com.example.demo.vo.resp.UserInfoResp;
import com.example.demo.vo.resp.UserPageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private UserRedis userRedis;


    @Override
    @Transactional
    public ApiResult saveUser(UserSaveReq userSaveReq) {
        if (!userSaveReq.getPwd().equals(userSaveReq.getConfirmPwd())) {
            log.warn("{} 密码与确认密码不相等！", userSaveReq.getUsername());
            return ApiResult.fail("密码与确认密码不相等！");
        }
        User user = new User();
        BeanUtil.copyProperties(userSaveReq, user);
        user.setUserState(StateType.Valid.value());
        user.setId(null);
        baseMapper.insert(user);
        Role role = roleMapper.selectById(userSaveReq.getRoleId());
        if (null == role) {
            return ApiResult.fail("角色不存在！");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleMapper.insert(userRole);
        return ApiResult.ok("添加用户成功！", null);
    }

    @Override
    @Transactional
    public ApiResult updateUser(UserSaveReq userSaveReq) {
        if (!userSaveReq.getPwd().equals(userSaveReq.getConfirmPwd())) {
            log.warn("{} 密码与确认密码不相等！", userSaveReq.getUsername());
            return ApiResult.fail("密码与确认密码不相等！");
        }
        User user = new User();
        user.setId(userSaveReq.getId());
        user.setPwd(userSaveReq.getPwd());
        user.setRemark(userSaveReq.getRemark());
        baseMapper.updateById(user);
        List<UserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        if (!userRoles.get(0).getRoleId().equals(userSaveReq.getRoleId())) {
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(userSaveReq.getRoleId());
            userRoleMapper.insert(userRole);
        }
        return ApiResult.ok("编辑用户成功！");
    }

    @Override
    @Transactional
    public ApiResult deleteUserById(Long id) {
        baseMapper.deleteById(id);
        return ApiResult.ok("删除用户成功！");
    }

    @Override
    public ApiResult doLogin(UserLoginReq userLoginReq) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user = baseMapper.selectOne(wrapper.eq("username", userLoginReq.getUsername()));
        if (null == user) {
            return ApiResult.fail("用户名不存在！");
        }
        String pwd = EncryptUtils.comRsaDecrypt(userLoginReq.getPwd());
        if (!user.getPwd().equals(pwd)) {
            return ApiResult.fail("密码输入错误！");
        }
        UserInfoResp userInfoResp = new UserInfoResp();
        userInfoResp.setId(user.getId());
        userInfoResp.setUsername(user.getUsername());
        userInfoResp.setUser(user);
        userInfoResp = setRoleInfo(userInfoResp);
        String token = IdUtil.simpleUUID();
        userRedis.setTokenUser(token, userInfoResp);
        userInfoResp.setToken(EncryptUtils.encryptToken(token));
        setMenuInfo(userInfoResp);
        return ApiResult.ok("登录成功", userInfoResp);
    }

    @Override
    public ApiResult logout(HttpServletRequest request, UserLoginReq userLoginReq) {
        String token = request.getHeader(ComConstant.AUTHORIZATION_KEY);
        token = StrUtil.isEmpty(token) ? request.getParameter(ComConstant.AUTHORIZATION_KEY) : token;
        if (StrUtil.isNotEmpty(token)) {
            userRedis.deleteTokenUser(EncryptUtils.decryptToken(token));
        }
        return ApiResult.ok("用户登出");
    }

    @Override
    public ApiResult pageList(UserQuery userQuery) {
        Page<User> page = new Page<>(userQuery.getPageNum(), userQuery.getPageSize());
        Page<UserPageResp> userPage = baseMapper.selectUserPage(page, userQuery);
        return ApiResult.ok("获取用户列表成功", new PageVO<UserPageResp>(userPage.getTotal(), userPage.getRecords()));
    }

    @Override
    @Transactional
    public ApiResult updateUserState(UserSaveReq userSaveReq) {
        User user = new User();
        user.setId(userSaveReq.getId());
        user.setUserState(userSaveReq.getUserState());
        baseMapper.updateById(user);
        return ApiResult.ok("更新用户状态成功！", null);
    }

    @Override
    public UserInfoResp setRoleInfo(UserInfoResp userInfoResp) {
        List<Role> roleList = roleMapper.getRoleListByUserId(userInfoResp.getId());
        List<String> roleNames = new ArrayList<>();
        roleList.forEach(role -> {
            roleNames.add(role.getRoleName());
        });
        userInfoResp.setRoles(roleList);
        userInfoResp.setRoleNames(roleNames);
        return userInfoResp;
    }

    @Override
    public ISecurityUser setMenuInfo(ISecurityUser iSecurityUser) {
        List<Role> roles = iSecurityUser.getRoles();
        List<Long> roleIds = new ArrayList<>();
        if (CollUtil.isNotEmpty(roles)) {
            roles.forEach(role -> {
                roleIds.add(role.getId());
            });
        }
        if (CollUtil.isNotEmpty(roleIds)) {
            Set<String> urls = new HashSet<>();
            Set<String> perms = new HashSet<>();
            List<Menu> menuList = new ArrayList<>();
            Map<Long, Menu> allMenu = LocalCacheManager.getAllMenu();
            List<RoleMenu> roleMenuList = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().in("role_id", roleIds));
            if (CollUtil.isNotEmpty(roleMenuList)) {
                roleMenuList.forEach(item -> {
                    Menu menu = allMenu.get(item.getMenuId());
                    if (Objects.nonNull(menu)) {
                        menuList.add(menu);
                    }
                });
            }
            List<MenuNodeVO> menuNodeVOS = new ArrayList<>();
            if (CollUtil.isNotEmpty(menuList)) {
                for (Menu menu : menuList) {
                    String url = menu.getUrl();
                    String per = menu.getPerms();
                    if (menu.getMenuType() == 0 && StrUtil.isNotEmpty(url)) {
                        urls.add(menu.getUrl());
                    }
                    if (menu.getMenuType() == 1 && StrUtil.isNotEmpty(per)) {
                        perms.add(menu.getPerms());
                    }
                    MenuNodeVO menuNodeVO = new MenuNodeVO();
                    BeanUtil.copyProperties(menu, menuNodeVO);
                    menuNodeVOS.add(menuNodeVO);
                }
            }
            List<MenuNodeVO> menus = MenuTreeUtils.build(menuNodeVOS);
            iSecurityUser.setUrls(urls);
            iSecurityUser.setPerms(perms);
            iSecurityUser.setMenus(menus);
        }
        return iSecurityUser;
    }
}
