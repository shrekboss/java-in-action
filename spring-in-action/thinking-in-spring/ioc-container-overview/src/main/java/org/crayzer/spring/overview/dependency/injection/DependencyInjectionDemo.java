package org.crayzer.spring.overview.dependency.injection;

import org.crayzer.spring.overview.domain.User;
import org.crayzer.spring.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入实例
 *
 * 
 */
public class DependencyInjectionDemo {

    /**
     * 依赖注入 和 依赖查找都属于依赖，但是来源是不一样的
     * <p/>
     * 1. 依赖来源一：自定义 Bean
     * <p/>
     * 2. 依赖来源二：容器内建依赖，非 Spring Bean，eg BeanFactory
     * <p/>
     * 3. 依赖来源三：容器内建，eg Environment
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/dependency-injection-context.xml");
        // BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INFO/dependency-injection-context.xml");
        // 依赖来源一：自定义 Bean
        System.out.println("==========华丽的分割线===========");
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        System.out.println("===依赖来源一：自定义 Bean " + userRepository);

        /*依赖注入*/
        // DefaultListableBeanFactory@614c5515
        // 依赖来源二：内建依赖(eg: BeanFactory)
        System.out.println("==========华丽的分割线===========");
        System.out.println("===依赖来源二：内建依赖 " + userRepository.getBeanFactory());

        System.out.println("ClassPathXmlApplicationContext: " + applicationContext);
        whoIsIoCContainer(applicationContext, userRepository.getBeanFactory());

        ObjectFactory<User> userObjectFactory = userRepository.getUserObjectFactory();
        System.out.println(userObjectFactory.getObject());

        ObjectFactory<ApplicationContext> objectFactory = userRepository.getApplicationContextObjectFactory();
        // ClassPathXmlApplicationContext
        System.out.println(objectFactory.getObject());
        // true
        System.out.println("private ObjectFactory<ApplicationContext> applicationContextObjectFactory()："
                + (objectFactory.getObject() == applicationContext));

        /*
         * 验证 BeanFactory 是否是一个普通的Bean? ===> 不是
         * No qualifying bean of type 'org.springframework.beans.factory.BeanFactory' available
         *
         * The BeanFactory interface provides an advanced configuration mechanism capable of managing any type of object.
         * */
        /*依赖查找*/
        // (错误)
        // System.out.println(beanFactory.getBean(BeanFactory.class));

        // 依赖来源三：容器内建 Bean (eg: Environment)
        System.out.println("==========华丽的分割线===========");
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("===依赖来源三：容器内建 Bean " + environment);
    }

    /**
     * ApplicationContext is BeanFactory
     * ConfigurableApplicationContext <- ApplicationContext <- BeanFacatory
     * ConfigurableApplicationContext#getBeanFactory() ===> private DefaultListableBeanFactory beanFactory;
     */
    private static void whoIsIoCContainer(ApplicationContext applicationContext, BeanFactory beanFactory) {
        System.out.println("userRepository.getBeanFactory() == applicationContext 是否相等：" + (beanFactory == applicationContext.getParentBeanFactory()));
    }

}
