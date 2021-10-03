package com.example.demo.controller;

import com.example.demo.common.controller.BaseController;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.service.UserService;
import com.example.demo.validation.UserValid;
import com.example.demo.vo.req.UserLoginReq;
import com.example.demo.vo.req.UserQuery;
import com.example.demo.vo.req.UserSaveReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @PostMapping("doLogin")
    @ApiOperation(value = "管理员登录")
    public ApiResult doLogin(@RequestBody @Valid UserLoginReq userLoginReq) {
        return userService.doLogin(userLoginReq);
    }

    @PostMapping("add")
//    @SaCheckLogin
    @ApiOperation(value = "添加用户")
    public ApiResult add(@RequestBody @Validated(UserValid.Insert.class) UserSaveReq userSaveReq) {
        return userService.saveUser(userSaveReq);
    }

    @PutMapping("update")
//    @SaCheckLogin
    @ApiOperation(value = "更新用户")
    public ApiResult update(@RequestBody @Validated(UserValid.Update.class) UserSaveReq userSaveReq) {
        return userService.updateUser(userSaveReq);
    }

    @DeleteMapping("delete/{id}")
//    @SaCheckLogin
    @ApiOperation(value = "删除用户")
    public ApiResult delete(@PathVariable String id) {
        return userService.deleteUserById(id);
    }


    @PostMapping("pageList")
//    @SaCheckLogin
    @ApiOperation(value = "查询用户分页列表")
    public ApiResult pageList(@RequestBody UserQuery userQuery) {
        return userService.pageList(userQuery);
    }

    @PostMapping("updateState")
//    @SaCheckLogin
    @ApiOperation(value = "更新用户状态")
    public ApiResult updateState(@RequestBody @Validated(UserValid.UpdateState.class) UserSaveReq userSaveReq) {
        return userService.updateUserState(userSaveReq);
    }


}
