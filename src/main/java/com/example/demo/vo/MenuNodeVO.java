package com.example.demo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class MenuNodeVO {

    private Long id;

    private Long parentId;

    private String label;

    private String url =null;

    private String icon;

    private Long orderNum;

    private Integer isOpen;

    private Integer available;

    private String perms;

    private Integer menuType;

    private List<MenuNodeVO> children = new ArrayList<>();

}
