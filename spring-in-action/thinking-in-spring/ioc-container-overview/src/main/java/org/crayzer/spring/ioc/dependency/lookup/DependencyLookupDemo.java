package org.crayzer.spring.ioc.dependency.lookup;

import org.crayzer.spring.ioc.annotation.Super;
import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找实例
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        // BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INFO/dependency-lookup-context.xml");
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/dependency-lookup-context.xml");

        /*通过名称查找对象*/
//        lookupInRealTime(beanFactory);
        /*通过名称懒加载查找对象*/
        lookupInLazy(beanFactory);
        /*通过类型查找对象*/
//        lookupByType(beanFactory);
        /*通过类型查找集合对象*/
//        lookupCollectionByType(beanFactory);
        /*通过注解查找集合对象*/
//        lookupByAnnotationType(beanFactory);
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Object> beansOfType = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找所有 @Super 的 User 集合对象: " + beansOfType);
        }
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("通过类型查找所有 User 集合对象: " + beansOfType);
        }
    }

    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("通过类型实时查找: " + user);
    }

    private static void lookupInLazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("通过名称延迟查找: " + user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("通过名称实时查找: " + user);
    }
}
