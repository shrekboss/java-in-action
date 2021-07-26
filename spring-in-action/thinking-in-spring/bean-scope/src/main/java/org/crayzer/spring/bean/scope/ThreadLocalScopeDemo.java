package org.crayzer.spring.bean.scope;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * 自定义 Scope {@link org.crayzer.spring.bean.scope.ThreadLocalScope} 示例
 * <p>
 * 日常开发中 SimpleDateFormat 是一个多线程不安全的
 * 通常会使用 prototype 的方式来每次调用创建一个新的实例
 * 不过可以通过 ThreadLocal 减少实例的创建
 *
 * @author Crayzer
 */
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    public User user() {
        return createUser();
    }

    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类） -> Spring Bean
        applicationContext.register(ThreadLocalScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(
                beanFactory -> {
                    // 注册自定义 scope
                    beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope());
                });

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        scopedBeansByLookup(applicationContext);

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    private static void scopedBeansByLookup(AnnotationConfigApplicationContext applicationContext) {

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> {
                // user 是共享 Bean 对象
                User user = applicationContext.getBean("user", User.class);
                System.out.printf("[Thread id :%d] user = %s%n", Thread.currentThread().getId(), user);
            });

            // 启动线程
            thread.start();
            // 强制线程执行完成
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
