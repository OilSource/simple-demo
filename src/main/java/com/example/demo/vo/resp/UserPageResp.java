package com.example.demo.vo.resp;

import lombok.Data;

import java.util.Date;
@Data
public class UserPageResp {

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新人
     */
    private String updater;


    /**
     * 状态
     */
    private Integer userState;

    private Long roleId;

    private String roleName;
}
