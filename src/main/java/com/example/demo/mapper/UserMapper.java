package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.vo.req.UserQuery;
import com.example.demo.vo.resp.UserPageResp;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    Page<UserPageResp> selectUserPage(Page page, @Param("query") UserQuery userQuery);
}