package org.crayzer.spring.bean.definition.factory;

import org.crayzer.spring.ioc.domain.User;

/**
 * @author Crayzer
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }
}
