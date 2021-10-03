package com.example.demo.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleAuthorityReq {

    @NotNull(message = "角色id不能为空")
    private Long roleId;

    private List<Long> menuIdList;
}
