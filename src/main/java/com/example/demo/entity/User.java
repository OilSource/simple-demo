package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName(value = "t_user")
public class User implements BaseInsertUpdateEntity,Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "creator",fill = FieldFill.INSERT)
    private String creator;

    /**
     * 更新人
     */
    @TableField(value = "updater",fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 状态
     */
    private Integer userState;
    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;


}