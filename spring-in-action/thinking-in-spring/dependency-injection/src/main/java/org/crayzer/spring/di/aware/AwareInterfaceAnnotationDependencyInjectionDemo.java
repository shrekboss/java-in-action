package org.crayzer.spring.di.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于 {@link Aware} Java 注解的依赖接口回调注入示例
 *
 * @author Crayzer
 */
public class AwareInterfaceAnnotationDependencyInjectionDemo implements BeanFactoryAware, ApplicationContextAware {

    private static BeanFactory beanFactory;
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类）
        ctx.register(AwareInterfaceAnnotationDependencyInjectionDemo.class);

        // 启动 Spring 应用上下文
        ctx.refresh();
        System.out.println(beanFactory == ctx.getBeanFactory());
        System.out.println(applicationContext == ctx);

        System.out.println();

        // 显示地关闭 Spring 应用上下文
        ctx.close();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareInterfaceAnnotationDependencyInjectionDemo.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AwareInterfaceAnnotationDependencyInjectionDemo.applicationContext = applicationContext;
    }
}
