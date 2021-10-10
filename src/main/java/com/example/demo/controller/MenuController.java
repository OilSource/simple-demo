package com.example.demo.controller;

import com.example.demo.common.controller.BaseController;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.service.MenuService;
import com.example.demo.vo.req.MenuSaveReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu/")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;


    @PostMapping("add")
    @ApiOperation(value = "添加菜单")
    public ApiResult add(@RequestBody @Valid MenuSaveReq menuSaveReq) throws JsonProcessingException {
        return menuService.saveMenu(menuSaveReq);
    }

    @PutMapping("update")
    @ApiOperation(value = "更新菜单")
    public ApiResult update(@RequestBody @Valid MenuSaveReq menuSaveReq) throws JsonProcessingException {
        return menuService.updateMenu(menuSaveReq);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除菜单")
    public ApiResult delete(@PathVariable String id) throws JsonProcessingException {
        return menuService.deleteByMenuId(id);
    }


    @GetMapping("tree")
    @ApiOperation(value = "查询菜单树")
    public ApiResult tree() {
        return menuService.tree();
    }

    @GetMapping("roleTree/{roleId}")
    @ApiOperation(value = "查询角色菜单树")
    public ApiResult roleTree(@PathVariable Long roleId) {
        return menuService.roleTree(roleId);
    }
}
