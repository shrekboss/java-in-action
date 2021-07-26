package org.crayzer.spring.bean.definition.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Crayzer
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct : UserFactory 初始化中...");
    }

    public void initUserFactory() {
        System.out.println("自定义方法 initUserFactory : UserFactory 初始化中...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet : UserFactory 初始化中...");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy : UserFactory 销毁中...");
    }

    public void destroyUserFactory() {
        System.out.println("自定义方法 destroyUserFactory : UserFactory 销毁中...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy : UserFactory 销毁中...");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("当前对象 DefaultUserFactory 对象正在被垃圾回收...");
    }
}
