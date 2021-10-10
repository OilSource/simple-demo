package com.example.demo.vo.req;

import com.example.demo.validation.ConstantValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ConstantSaveReq {
    @NotNull(message = "id不能为空",groups = {ConstantValid.UpdateState.class,ConstantValid.Update.class})
    private Long id;
    @NotBlank(message = "常量key不能为空",groups = ConstantValid.Insert.class)
    private String constKey;
    @NotBlank(message = "常量value不能为空",groups = ConstantValid.Insert.class)
    private String constValue;
    @NotNull(message = "状态不能为空",groups = ConstantValid.UpdateState.class)
    private Integer constStatus;
    private String remark;

}
