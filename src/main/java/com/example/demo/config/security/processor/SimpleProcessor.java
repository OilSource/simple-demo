package com.example.demo.config.security.processor;

import com.example.demo.config.security.provider.SimpleAccountProvider;
import com.example.demo.config.security.subject.SimpleSubject;
import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.subject.Subject;
import lombok.Setter;

public class SimpleProcessor extends BaseProcessor {

    @Setter
    private SimpleAccountProvider simpleAccountProvider;

    @Override
    public boolean canSupportSubjectClass(Class<?> aClass) {
        return aClass == SimpleSubject.class;
    }

    @Override
    public Class<?> getSupportSubjectClass() {
        return null;
    }

    @Override
    public Subject authenticated(Subject subject) throws SurenessAuthenticationException {
        return null;
    }
}
