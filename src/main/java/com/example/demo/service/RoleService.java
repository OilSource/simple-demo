package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.dto.resp.ApiResult;
import com.example.demo.entity.Role;
import com.example.demo.vo.req.RoleAuthorityReq;
import com.example.demo.vo.req.RoleQuery;
import com.example.demo.vo.req.RoleSaveReq;
import com.example.demo.vo.req.UserSaveReq;

import java.util.List;

public interface RoleService extends IService<Role> {


    /**
     * 添加用户
     */
    ApiResult saveRole(RoleSaveReq roleSaveReq);

    /**
     * 更新用户
     */
    ApiResult updateRole(RoleSaveReq roleSaveReq);

    /**
     * 删除用户
     */
    ApiResult deleteRoleById(String id);

    /**
     * 用户分页
     */
    ApiResult pageList(RoleQuery RoleQuery);

    ApiResult listRole();

    ApiResult updateRoleState(RoleSaveReq roleSaveReq);

    ApiResult authority(RoleAuthorityReq roleAuthorityReq);
}
