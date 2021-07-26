package org.crayzer.spring.bean.definition;

import org.crayzer.spring.bean.definition.factory.DefaultUserFactory;
import org.crayzer.spring.bean.definition.factory.UserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 特殊的 Bean 实例化示例
 * <p>
 * 1. 通过 ServiceLoaderFactoryBean（配置元信息：XML、Java 注解和 Java API ）
 * <p>
 * 2. 通过 AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)
 * <p>
 * 3. 通过 BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)
 *
 * @author Crayzer
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/special-bean-instantiation-context.xml");
        // 通过 ApplicationContext 获取 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);

        displayServiceLoader(serviceLoader);

        // demoServiceLoader();

        // 创建 UserFactory 对象，通过 AutowireCapableBeanFactory
        UserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
        System.out.println("通过 AutowireCapableBeanFactory 创建：" + userFactory.createUser());

    }

    public static void demoServiceLoader() {
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        displayServiceLoader(serviceLoader);
    }

    private static void displayServiceLoader(ServiceLoader<UserFactory> serviceLoader) {
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory userFactory = iterator.next();
            System.out.println(userFactory.createUser());
        }
    }
}
