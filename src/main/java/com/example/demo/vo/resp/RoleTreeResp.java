package com.example.demo.vo.resp;

import com.example.demo.vo.MenuNodeVO;
import lombok.Data;

import java.util.List;

@Data
public class RoleTreeResp {

    private Long roleId;

    private List<Long> menuIdList;

    private List<MenuNodeVO> tree;

}
