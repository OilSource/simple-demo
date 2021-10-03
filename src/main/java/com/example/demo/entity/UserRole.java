package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "t_user_role")
public class UserRole implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;

    private static final long serialVersionUID = 1L;
}