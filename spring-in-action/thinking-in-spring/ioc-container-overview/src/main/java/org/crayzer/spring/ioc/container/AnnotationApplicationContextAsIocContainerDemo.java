package org.crayzer.spring.ioc.container;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;


/**
 * 具有注解能力 {@link ApplicationContext} Ioc 容器示例
 *
 * 
 */
public class AnnotationApplicationContextAsIocContainerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 AnnotationApplicationContextAsIocContainerDemo 作为配置类(Configuration class)
        applicationContext.register(AnnotationApplicationContextAsIocContainerDemo.class);
        // 启动容器上下文
        applicationContext.refresh();
        lookupByCollectionType(applicationContext);
        // 结束容器上下文
        applicationContext.close();
    }

    /**
     * 通过 Java 注解的方式定义 Bean
     **/
    @Bean
    public User user() {
        return User.createUser();
    }

    private static void lookupByCollectionType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("通过类型查找所有 User 集合对象: " + beansOfType);
        }
    }
}
