package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_role_menu")
public class RoleMenu implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long roleId;

    private Long menuId;

    private static final long serialVersionUID = 1L;
}