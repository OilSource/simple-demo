package com.example.demo.controller;

import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.service.ConstantService;
import com.example.demo.validation.ConstantValid;
import com.example.demo.vo.req.ConstantQuery;
import com.example.demo.vo.req.ConstantSaveReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "常量管理")
@RestController
@RequestMapping("/constant/")
public class ConstantController {

    @Resource
    private ConstantService constantService;


    @PostMapping("pageList")
    @ApiOperation(value = "查询常量分页")
    public ApiResult pageList(@RequestBody ConstantQuery constantQuery) {
        return constantService.pageList(constantQuery);
    }

    @PostMapping("add")
    @ApiOperation(value = "添加常量")
    public ApiResult add(@RequestBody @Validated(ConstantValid.Insert.class) ConstantSaveReq constantSaveReq) throws JsonProcessingException {
        return constantService.saveConstant(constantSaveReq);
    }

    @PutMapping("update")
    @ApiOperation(value = "更新常量")
    public ApiResult update(@RequestBody @Validated(ConstantValid.Update.class) ConstantSaveReq constantSaveReq) throws JsonProcessingException {
        return constantService.updateConstant(constantSaveReq);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除常量")
    public ApiResult delete(@PathVariable Long id) throws JsonProcessingException {
        return constantService.deleteConstantById(id);
    }

    @PostMapping("updateState")
    @ApiOperation(value = "更新用户状态")
    public ApiResult updateState(@RequestBody @Validated(ConstantValid.UpdateState.class) ConstantSaveReq constantSaveReq) throws JsonProcessingException {
        return constantService.updateConstantStatus(constantSaveReq);
    }

}
