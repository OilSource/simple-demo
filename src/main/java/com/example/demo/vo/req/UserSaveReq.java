package com.example.demo.vo.req;

import com.example.demo.validation.UserValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserSaveReq {
    @NotNull(message = "id不能为空", groups = {UserValid.UpdateState.class, UserValid.Update.class})
    private Long id;
    @NotBlank(message = "用户不能为空", groups = UserValid.Insert.class)
    private String username;
    @NotBlank(message = "密码不能为空", groups = {UserValid.Insert.class, UserValid.Update.class})
    private String pwd;
    @NotBlank(message = "确认密码不能为空", groups = {UserValid.Insert.class, UserValid.Update.class})
    private String confirmPwd;
    private String remark;
    @NotNull(message = "用户状态不能为空", groups = UserValid.UpdateState.class)
    private Integer userState;
    @NotNull(message = "角色不能为空", groups = UserValid.Insert.class)
    private Long roleId;
}
