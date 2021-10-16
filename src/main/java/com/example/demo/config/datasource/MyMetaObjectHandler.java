package com.example.demo.config.datasource;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.demo.vo.UserInfoVO;
import com.usthe.sureness.util.SurenessContextHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        UserInfoVO userInfoVO = (UserInfoVO)SurenessContextHolder.getBindSubject();
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "creator", String.class, userInfoVO.getUsername());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updater", String.class, userInfoVO.getUsername());
    }




    @Override
    public void updateFill(MetaObject metaObject) {
        UserInfoVO userInfoVO = (UserInfoVO)SurenessContextHolder.getBindSubject();
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updater", String.class, userInfoVO.getUsername());
    }

}
