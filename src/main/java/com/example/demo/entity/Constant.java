package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * t_constant
 */
@Data
@TableName(value = "t_constant")
public class Constant implements BaseInsertUpdateEntity, Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 常量key
     */
    private String constKey;

    /**
     * 常量vlaue
     */
    private String constValue;

    /**
     * 0无效1有效
     */
    private Integer constStatus;

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
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}