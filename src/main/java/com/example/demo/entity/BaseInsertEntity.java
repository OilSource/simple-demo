package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public interface BaseInsertEntity {

    Date getCreateTime();

    void setCreateTime(Date createTime);

    String getCreator();

    void setCreator(String creator);
}
