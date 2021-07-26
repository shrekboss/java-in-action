package org.crayzer.spring.bean.definition.factory;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Crayzer
 */
public class UserFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
