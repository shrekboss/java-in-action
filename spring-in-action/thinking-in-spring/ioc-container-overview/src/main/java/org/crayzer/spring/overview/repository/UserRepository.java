package org.crayzer.spring.overview.repository;

import lombok.Data;
import org.crayzer.spring.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

@Data
public class UserRepository {

    /**
     * 自定义 Bean
     */
    private Collection<User> users;
    /**
     * 容器内建依赖
     */
    private BeanFactory beanFactory;

    // 延迟注入
    private ObjectFactory<User> userObjectFactory;
    private ObjectFactory<ApplicationContext> applicationContextObjectFactory;
}
