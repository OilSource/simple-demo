package com.example.demo.vo.req;

import com.example.demo.common.dto.req.BasePageQuery;
import lombok.Data;

@Data
public class RoleQuery extends BasePageQuery {

    private String roleName;
}
