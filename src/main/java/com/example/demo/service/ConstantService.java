package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.entity.Constant;
import com.example.demo.vo.req.ConstantQuery;
import com.example.demo.vo.req.ConstantSaveReq;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface ConstantService extends IService<Constant> {

    ApiResult pageList(ConstantQuery constantQuery);

    ApiResult saveConstant(ConstantSaveReq constantSaveReq) throws JsonProcessingException;

    Constant getIgnoreUrl();

    ApiResult deleteConstantById(Long id) throws JsonProcessingException;

    ApiResult updateConstantStatus(ConstantSaveReq constantSaveReq) throws JsonProcessingException;

    ApiResult updateConstant(ConstantSaveReq constantSaveReq) throws JsonProcessingException;
}
