package com.example.demo.config.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import com.example.demo.config.security.processor.SimpleProcessor;
import com.example.demo.config.security.subject.SimpleSubjectCreator;
import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.TreePathRoleMatcher;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.ProcessorManager;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.provider.annotation.AnnotationPathTreeProvider;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.SurenessSubjectFactory;
import com.usthe.sureness.subject.creater.NoneSubjectServletCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.*;

@Configuration
public class SecurityConfig {

    @Bean
    ProcessorManager processorManager(SimpleProcessor simpleProcessor) {
        // process init
        List<Processor> processorList = new LinkedList<>();
        processorList.add(new NoneProcessor());
        processorList.add(simpleProcessor);
        return new DefaultProcessorManager(processorList);
    }

    /**
     * @param pathTreeProvider the path tree resource load from database
     */
    @Bean
    TreePathRoleMatcher pathRoleMatcher(PathTreeProvider pathTreeProvider, ResourceLoader resourceLoader) throws IOException {
        AnnotationPathTreeProvider annotationPathTreeProvider = new AnnotationPathTreeProvider();
        String packages = "com.simple.demo.controller";
        annotationPathTreeProvider.setScanPackages(Collections.singletonList(packages));
        ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = resourcePatternResolver.getResources("classpath*:" + packages.replace(".", "/") + "/**/*.class");
        ArrayList<Class> list = new ArrayList<>();
        for (Resource resource : resources) {
            MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
            String className = metadataReader.getClassMetadata().getClassName();
            list.add(ClassUtil.loadClass(className));
        }
        BeanUtil.setProperty(annotationPathTreeProvider, "isInit", true);
        BeanUtil.setProperty(annotationPathTreeProvider, "scanClasses", list);

        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProviderList(Arrays.asList(
                annotationPathTreeProvider,
                pathTreeProvider));
        pathRoleMatcher.buildTree();
        return pathRoleMatcher;
    }

    @Bean
    SubjectFactory subjectFactory() {
        // SubjectFactory init
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        subjectFactory.registerSubjectCreator(Arrays.asList(
                new NoneSubjectServletCreator(),
                new SimpleSubjectCreator()));
        return subjectFactory;
    }

    @Bean
    SurenessSecurityManager securityManager(ProcessorManager processorManager,
                                            TreePathRoleMatcher pathRoleMatcher, SubjectFactory subjectFactory) {
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        return securityManager;
    }


}
