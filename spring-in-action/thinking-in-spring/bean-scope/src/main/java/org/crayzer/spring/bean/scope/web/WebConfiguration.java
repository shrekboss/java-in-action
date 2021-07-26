package org.crayzer.spring.bean.scope.web;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Crayzer
 */
@Configuration
@EnableWebMvc
public class WebConfiguration {

    @Bean
    /*{@see RequestScope.java}*/
    // @RequestScope
    /*{@see SessionScope.java}*/
//    @SessionScope
    /*{@see ServletContextScope.java}*/
    @ApplicationScope
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("马克图布");
        return user;
    }

}
