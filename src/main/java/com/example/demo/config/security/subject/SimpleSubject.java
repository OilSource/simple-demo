package com.example.demo.config.security.subject;

import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.Subject;

import java.util.List;

public class SimpleSubject implements Subject {

    private String token;

    private String targetUri;

    private String remoteHost;

    private List<String> supportRoles;

    public SimpleSubject(String token, String targetUri, String remoteHost) {
        this.token = token;
        this.targetUri = targetUri;
        this.remoteHost = remoteHost;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public void setPrincipal(Object o) {

    }

    @Override
    public PrincipalMap getPrincipalMap() {
        return null;
    }

    @Override
    public void setPrincipalMap(PrincipalMap principalMap) {

    }

    @Override
    public Object getCredential() {
        return null;
    }

    @Override
    public void setCredential(Object o) {

    }

    @Override
    public Object getOwnRoles() {
        return null;
    }

    @Override
    public void setOwnRoles(Object o) {

    }

    @Override
    public Object getTargetResource() {
        return targetUri;
    }

    @Override
    public void setTargetResource(Object o) {
        this.targetUri = (String) o;
    }

    @Override
    public Object getSupportRoles() {
        return supportRoles;
    }

    @Override
    public void setSupportRoles(Object o) {
        supportRoles = (List<String>) o;
    }
}
