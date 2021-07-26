package org.crayzer.spring.bean.definition.lifecycle;

import org.crayzer.spring.bean.definition.factory.DefaultUserFactory;
import org.crayzer.spring.bean.definition.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Bean 初始化（Initialization）示例
 * <p>
 * 1. @PostConstruct 标注方法
 * <p>
 * 2. 实现 InitializingBean 接口的 afterPropertiesSet() 方法
 * <p>
 * 3. 自定义初始化方法
 * <p>
 * a. XML 配置：<bean init-method=”init” ... />
 * <p>
 * b. Java 注解：@Bean(initMethod=”init”)
 * <p>
 * c. Java API：AbstractBeanDefinition#setInitMethodName(String)
 * <p>
 * 优先级：@PostConstruct > InitializingBean#afterPropertiesSet > 自定义方法 initUserFactory
 * <p>
 * 延迟初始化 @Lazy
 * <p>
 * 延迟初始化在 Spring 应用上下文启动后，第一次被调用的被初始化
 *
 * @author Crayzer
 */
@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 BeanInitializationDemo 作为配置类(Configuration class)
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        // 延迟初始化在 Spring 应用上下文启动后，第一次被调用的被初始化
        System.out.println("Spring 应用上下文已启动");

        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);

        applicationContext.close();
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "destroyUserFactory")
//    @Lazy(value = false)
     @Lazy
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }
}
