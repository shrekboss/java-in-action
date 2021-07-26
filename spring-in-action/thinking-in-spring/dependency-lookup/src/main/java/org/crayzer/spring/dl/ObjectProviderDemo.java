package org.crayzer.spring.dl;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 通过 {@link ObjectProvider} 实现依赖查找示例
 *
 * @author Crayzer
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 BeanProviderDemo 作为配置类(Configuration class)
        applicationContext.register(ObjectProviderDemo.class);
        // 启动容器上下文
        applicationContext.refresh();

        lookupByBeanProvide(applicationContext);
        lookupIfAvailable(applicationContext);
        lookupByStreamOps(applicationContext);

        // 结束容器上下文
        applicationContext.close();
    }

    @Bean
    @Primary
    public String helloWorld() {
        return "Hello World~!";
    }

    @Bean
    public String message() {
        return "Message";
    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        // Iterable<String> stringIterable = objectProvider;
        // for (String str : stringIterable) {
        //     System.out.println(str);
        // }
        objectProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        User user = beanProvider.getIfAvailable(User::createUser);
        System.out.println("当前 User 对象：" + user);
    }

    private static void lookupByBeanProvide(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(beanProvider.getObject());
    }
}
