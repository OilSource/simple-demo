package com.example.demo.vo.resp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class ConstantPageResp {

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
     * 备注
     */
    private String remark;
}
