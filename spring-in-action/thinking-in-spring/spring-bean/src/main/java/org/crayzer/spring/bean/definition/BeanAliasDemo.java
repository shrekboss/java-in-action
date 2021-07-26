package org.crayzer.spring.bean.definition;

import org.crayzer.spring.overview.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 别名示例
 *
 * @author Crayzer
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definition-context.xml");
        User user = (User) applicationContext.getBean("user");
        User aUser = (User) applicationContext.getBean("aUser");
        System.out.println("user == aUser ：" + (user == aUser));
    }
}
