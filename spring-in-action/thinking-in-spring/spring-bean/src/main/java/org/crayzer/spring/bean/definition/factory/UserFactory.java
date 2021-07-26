package org.crayzer.spring.bean.definition.factory;

import org.crayzer.spring.overview.domain.User;

/**
 * @author Crayzer
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }
}
