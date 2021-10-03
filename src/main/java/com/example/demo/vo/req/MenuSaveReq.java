package com.example.demo.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MenuSaveReq {

    private Long id;
    @NotBlank(message = "菜单名称不能为空！")
    private String label;

    private String url;

    private String perms;

    private String icon;

    private Integer available;

    private Integer isOpen;

    private Integer orderNum;

    private Integer menuType;

    private Long parentId;

}
