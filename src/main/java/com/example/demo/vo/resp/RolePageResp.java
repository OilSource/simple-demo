package com.example.demo.vo.resp;

import lombok.Data;

import java.util.Date;

@Data
public class RolePageResp {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 状态
     */
    private Integer roleState;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String remark;
}
