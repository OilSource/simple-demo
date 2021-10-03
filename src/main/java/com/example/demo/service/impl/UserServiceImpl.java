package com.example.demo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.common.dto.resp.PageVO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.enums.StateType;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.service.UserService;
import com.example.demo.vo.req.UserLoginReq;
import com.example.demo.vo.req.UserQuery;
import com.example.demo.vo.req.UserSaveReq;
import com.example.demo.vo.resp.UserPageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public ApiResult saveUser(UserSaveReq userSaveReq) {
        if(!userSaveReq.getPwd().equals(userSaveReq.getConfirmPwd())){
            log.warn("{} 密码与确认密码不相等！",userSaveReq.getUsername());
            return ApiResult.fail("密码与确认密码不相等！");
        }
        User user = new User();
        BeanUtil.copyProperties(userSaveReq,user);
        user.setUserState(StateType.Valid.value());
        user.setId(null);
        baseMapper.insert(user);
        Role role = roleMapper.selectById(userSaveReq.getRoleId());
        if(null == role){
            return ApiResult.fail("角色不存在！");
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleMapper.insert(userRole);
        return ApiResult.ok("添加用户成功！",null);
    }

    @Override
    public ApiResult updateUser(UserSaveReq userSaveReq) {
        if(!userSaveReq.getPwd().equals(userSaveReq.getConfirmPwd())){
            log.warn("{} 密码与确认密码不相等！",userSaveReq.getUsername());
            return ApiResult.fail("密码与确认密码不相等！");
        }
        User user = new User();
        user.setPwd(userSaveReq.getPwd());
        user.setRemark(userSaveReq.getRemark());
        baseMapper.updateById(user);
        return ApiResult.ok("编辑用户成功！",null);
    }

    @Override
    public ApiResult deleteUserById(String id) {
        baseMapper.deleteById(id);
        return ApiResult.ok("删除用户成功！",null);
    }

    @Override
    public ApiResult doLogin(UserLoginReq userLoginReq) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user = baseMapper.selectOne(wrapper.eq("username", userLoginReq.getUsername()));
        if(null == user){
            return ApiResult.fail("用户名不存在！");
        }
        if(!user.getPwd().equals(userLoginReq.getPwd())){
            return ApiResult.fail("密码输入错误！");
        }
        try {
            StpUtil.login(user.getId());
            return ApiResult.ok("登录成功", StpUtil.getTokenInfo());
        } catch (Exception e) {
            log.error("登录失败！",e);
        }
        return ApiResult.fail("登录失败");
    }

    @Override
    public ApiResult pageList(UserQuery userQuery) {
        Page<User> page = new Page<>(userQuery.getPageNum(), userQuery.getPageSize());
        Page<UserPageResp> userPage = baseMapper.selectUserPage(page, userQuery);
        return ApiResult.ok("获取用户列表成功", new PageVO<UserPageResp>(userPage.getTotal(), userPage.getRecords()));
    }

    @Override
    public ApiResult updateUserState(UserSaveReq userSaveReq) {
        User user = new User();
        user.setId(userSaveReq.getId());
        user.setUserState(userSaveReq.getUserState());
        baseMapper.updateById(user);
        return ApiResult.ok("更新用户状态成功！",null);
    }
}
