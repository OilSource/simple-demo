package com.example.demo.config.security.subject;

import cn.hutool.core.util.StrUtil;
import com.example.demo.constants.ComConstant;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;

import javax.servlet.http.HttpServletRequest;

public class SimpleSubjectCreator implements SubjectCreate {

    @Override
    public boolean canSupportSubject(Object context) {
        if(context instanceof HttpServletRequest){
            HttpServletRequest request = (HttpServletRequest) context;
            String authHeader = request.getHeader(ComConstant.AUTHORIZATION_KEY);
            boolean authFlag= StrUtil.isNotBlank(authHeader);
            if(!authFlag){
                authFlag =request.getParameterMap().containsKey(ComConstant.AUTHORIZATION_KEY);
            }
            return authFlag;
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        HttpServletRequest request = (HttpServletRequest) context;
        String token = request.getHeader(ComConstant.AUTHORIZATION_KEY);
        token = StrUtil.isBlank(token)?request.getParameter(ComConstant.AUTHORIZATION_KEY):token;
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String targetUri = requestURI.concat("===").concat(method.toLowerCase());
        String remoteHost = request.getRemoteHost();
        return new SimpleSubject(token,targetUri,remoteHost);
    }
}
