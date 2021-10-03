package com.example.demo.vo.req;

import com.example.demo.validation.UserValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleSaveReq {
    @NotNull(message = "id不能为空", groups = {UserValid.UpdateState.class, UserValid.Update.class})
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    private String remark;
    @NotNull(message = "角色状态不能为空", groups = UserValid.UpdateState.class)
    private Integer roleState;
}
