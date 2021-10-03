package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName(value = "t_role")
public class Role implements BaseInsertUpdateEntity,Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "updater",fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 状态
     */
    private Integer roleState;

    /**
     * 创建人
     */
    @TableField(value = "creator",fill = FieldFill.INSERT)
    private String creator;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String remark;

    private static final long serialVersionUID = 1L;
}