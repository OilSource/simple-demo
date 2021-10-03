package com.example.demo.entity;

import java.util.Date;

public interface BaseUpdateEntity {

    Date getUpdateTime();

    void setUpdateTime(Date updateTime);

    String getUpdater();

    void setUpdater(String updater);
}
