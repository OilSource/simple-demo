package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName(value = "t_menu")
public class Menu implements BaseInsertUpdateEntity, Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String icon;

    private Integer parentId;

    private String label;

    /**
     * 排序序号
     */
    private Integer orderNum;


    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单url
     */
    private String url;


    /**
     * 0不可用1可用
     */
    private Integer available;

    /**
     * 0不展开1展开
     */
    private Integer isOpen;

    /**
     * 0菜单1按钮
     */
    private Integer menuType;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "creator",fill = FieldFill.INSERT)
    private String creator;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value = "updater",fill = FieldFill.INSERT_UPDATE)
    private String updater;


    private static final long serialVersionUID = 1L;
}