package org.crayzer.spring.bean.definition.instantiation;

import org.crayzer.spring.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 实例化示例
 * <p>
 * 1. 通过构造器（配置元信息：XML、Java 注解和 Java API ）
 * <p>
 * 2. 通过静态工厂方法（配置元信息：XML 和 Java API ）
 * <p>
 * 3. 通过 Bean 工厂方法（配置元信息：XML和 Java API ）
 * <p>
 * 4. 通过 FactoryBean（配置元信息：XML、Java 注解和 Java API ）
 *
 * @author Crayzer
 */
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/bean-instantiation-context.xml");
        User userByStaticMethod = beanFactory.getBean("user-by-static-method", User.class);
        User userByInstanceMethod = beanFactory.getBean("user-by-instance-method", User.class);
        User userByFactoryBean = beanFactory.getBean("user-by-factory-bean", User.class);
        System.out.println(userByStaticMethod);
        System.out.println(userByInstanceMethod);
        System.out.println(userByFactoryBean);
        System.out.println(userByStaticMethod == userByInstanceMethod);
        System.out.println(userByStaticMethod == userByFactoryBean);
    }
}
