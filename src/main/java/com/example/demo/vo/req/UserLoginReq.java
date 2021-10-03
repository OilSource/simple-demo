package com.example.demo.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginReq {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String pwd;
}
