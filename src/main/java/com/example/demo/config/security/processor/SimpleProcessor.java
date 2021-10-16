package com.example.demo.config.security.processor;

import cn.hutool.core.util.StrUtil;
import com.example.demo.config.security.subject.SimpleSubject;
import com.example.demo.redis.UserRedis;
import com.example.demo.service.UserService;
import com.example.demo.utils.EncryptUtils;
import com.example.demo.vo.UserInfoVO;
import com.example.demo.vo.resp.UserInfoResp;
import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleProcessor extends BaseProcessor {

    @Autowired
    private UserRedis userRedis;

    @Autowired
    private UserService userService;

    @Override
    public boolean canSupportSubjectClass(Class<?> aClass) {
        return aClass == SimpleSubject.class;
    }

    @Override
    public Class<?> getSupportSubjectClass() {
        return SimpleSubject.class;
    }

    @Override
    public Subject authenticated(Subject subject) throws SurenessAuthenticationException {
        SimpleSubject simpleSubject = (SimpleSubject) subject;
        String encryptToken = (String) simpleSubject.getPrincipal();
        if (StrUtil.isEmpty(encryptToken)) {
            throw new IncorrectCredentialsException("token 为空");
        }
        String token = EncryptUtils.decryptToken(encryptToken);
        if (StrUtil.isEmpty(token)) {
            throw new IncorrectCredentialsException("token 异常");
        }
        UserInfoResp tokenUser = userRedis.getTokenUser(token);
        if (null == tokenUser) {
            throw new ExpiredCredentialsException("token 过期");
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(tokenUser.getId());
        userInfoVO.setUsername(tokenUser.getUsername());
        userInfoVO.setUser(tokenUser.getUser());
        userInfoVO.setRoles(tokenUser.getRoles());
        userInfoVO.setRoleNames(tokenUser.getRoleNames());
        userInfoVO.setToken(token);
        userInfoVO.setTargetResource((String) subject.getTargetResource());
        userService.setMenuInfo(userInfoVO);
        simpleSubject.setUserInfoVO(userInfoVO);
        userRedis.refreshTokenUser(token);
        return simpleSubject;
    }

    @Override
    public void authorized(Subject var) throws SurenessAuthorizationException {
        super.authorized(var);
    }
}
