package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.entity.User;
import com.example.demo.vo.req.UserLoginReq;
import com.example.demo.vo.req.UserQuery;
import com.example.demo.vo.req.UserSaveReq;

public interface UserService extends IService<User> {
    /**
     * 添加用户
     */
    ApiResult saveUser(UserSaveReq userSaveReq);

    /**
     * 更新用户
     */
    ApiResult updateUser(UserSaveReq userSaveReq);
    /**
     * 删除用户
     */
    ApiResult deleteUserById(String id);
    /**
     * 用户登录
     */
    ApiResult doLogin(UserLoginReq userLoginReq);
    /**
     * 用户分页
     */
    ApiResult pageList(UserQuery userQuery);

    ApiResult updateUserState(UserSaveReq userSaveReq);


}
