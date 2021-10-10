package com.example.demo.controller;

import com.example.demo.common.controller.BaseController;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.service.RoleService;
import com.example.demo.validation.UserValid;
import com.example.demo.vo.req.RoleAuthorityReq;
import com.example.demo.vo.req.RoleQuery;
import com.example.demo.vo.req.RoleSaveReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/role/")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;


    @PostMapping("add")
    @ApiOperation(value = "添加角色")
    public ApiResult add(@RequestBody @Valid RoleSaveReq roleSaveReq) {
        return roleService.saveRole(roleSaveReq);
    }

    @PutMapping("update")
    @ApiOperation(value = "更新角色")
    public ApiResult update(@RequestBody @Valid RoleSaveReq roleSaveReq) {
        return roleService.updateRole(roleSaveReq);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除角色")
    public ApiResult delete(@PathVariable String id) {
        return roleService.deleteRoleById(id);
    }


    @PostMapping("pageList")
    @ApiOperation(value = "查询角色分页列表")
    public ApiResult pageList(@RequestBody RoleQuery roleQuery) {
        return roleService.pageList(roleQuery);
    }


    @PostMapping("list")
    @ApiOperation(value = "查询角色列表")
    public ApiResult list() {
        return roleService.listRole();
    }

    @PostMapping("updateState")
    @ApiOperation(value = "更新角色状态")
    public ApiResult updateState(@RequestBody @Validated(UserValid.UpdateState.class) RoleSaveReq roleSaveReq) {
        return roleService.updateRoleState(roleSaveReq);
    }

    @PostMapping("authority")
    @ApiOperation(value = "角色授权")
    public ApiResult authority(@RequestBody RoleAuthorityReq roleAuthorityReq) {
        return roleService.authority(roleAuthorityReq);
    }

}
