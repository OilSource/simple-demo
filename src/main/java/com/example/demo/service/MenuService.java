package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.entity.Menu;
import com.example.demo.vo.req.MenuSaveReq;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface MenuService extends IService<Menu> {

    ApiResult saveMenu(MenuSaveReq menuSaveReq);

    ApiResult tree();

    ApiResult updateMenu(MenuSaveReq menuSaveReq);

    ApiResult deleteByMenuId( String id);

    ApiResult roleTree(Long roleId);
}
