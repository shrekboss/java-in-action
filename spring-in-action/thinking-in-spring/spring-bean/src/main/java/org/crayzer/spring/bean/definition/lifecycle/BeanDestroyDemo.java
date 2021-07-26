package org.crayzer.spring.bean.definition.lifecycle;

import org.crayzer.spring.bean.definition.factory.DefaultUserFactory;
import org.crayzer.spring.bean.definition.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 销毁（Destroy）示例
 * <p>
 * 1. @PreDestroy 标注方法
 * <p>
 * 2. 实现 DisposableBean 接口的 destroy() 方法
 * <p>
 * 3. 自定义销毁方法
 * <p>
 * a. XML 配置：<bean destroy=”destroy” ... />
 * <p>
 * b. Java 注解：@Bean(destroy=”destroy”)
 * <p>
 * c. Java API：AbstractBeanDefinition#setDestroyMethodName(String)
 * <p>
 * 优先级：@PreDestroy > DisposableBean#destroy > 自定义方法 destroyUserFactory
 *
 * @author Crayzer
 */
@Configuration
public class BeanDestroyDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 AnnotationApplicationContextAsIocContainerDemo 作为配置类(Configuration class)
        applicationContext.register(BeanDestroyDemo.class);
        applicationContext.refresh();
        // 非延迟初始化在 Spring 应用上下文启动后，被初始化
        // destory() 是由 applicationContext.close() 触发
        System.out.println("Spring 应用上下文已启动");

        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring 应用上下文准备关闭...");

        applicationContext.close();
        System.out.println("Spring 应用上下文正在关闭...");
    }

    @Bean(destroyMethod = "destroyUserFactory")
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }
}
