package org.crayzer.spring.di;

import org.crayzer.spring.di.annotation.InjectedUser;
import org.crayzer.spring.di.annotation.MyAutowired;
import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

/**
 * 注解驱动的依赖注入处理过程
 * <p>
 * 1. 入口 - DefaultListableBeanFactory#resolveDependency
 * <p>
 * 2. 依赖描述符 - DependencyDescriptor
 * <p>
 * 3. 自定绑定候选对象处理器 - AutowireCandidateResolver
 * <p>
 * {@link Autowired} 注入过程
 * 1. 元信息解析：主要关注字段的操作
 * <p>
 * 2. 依赖查找
 * <p>
 * AutowiredAnnotationBeanPostProcessor#postProcessMergedBeanDefinition(..) 进行元数据的查找
 * <p>
 * AutowiredAnnotationBeanPostProcessor#postProcessProperties(..) 注入(包含 依赖查找)
 * <p>
 * 3. 依赖注入（字段、方法）
 *
 * @author Crayzer
 */
@Configuration
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired          // 依赖查找（处理） + 延迟
    @Lazy
    private User lazyUser;

    // DependencyDescriptor ->
    // 必须（required=true）
    // 实时注入（eager=true)
    // 通过类型（User.class）
    // 字段名称（"user"）
    // 是否首要（primary = true)
    @Autowired          // 依赖查找（处理）
    private User user;

    @Autowired          // 集合类型依赖注入
    private Map<String, User> users; // user superUser

    @MyAutowired
    private Optional<User> userOptional; // superUser

    @Inject
    private User injectedUser;

    @InjectedUser
    private User myInjectedUser;

    // static 可以提前 Bean 的注册和初始化
    // @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    // public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
    //     AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
    //     // @Autowired + @Inject +  新注解 @InjectedUser
    //     Set<Class<? extends Annotation>> autowiredAnnotationTypes =
    //             new LinkedHashSet<>(Arrays.asList(Autowired.class, Inject.class, InjectedUser.class));
    //     beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
    //     return beanPostProcessor;
    // }

    // static 可以提前 Bean 的注册和初始化
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
        return beanPostProcessor;
    }

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类） -> Spring Bean
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 QualifierAnnotationDependencyInjectionDemo Bean
        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        System.out.println("demo.injectedUser = " + demo.injectedUser);
        System.out.println("demo.userOptional = " + demo.userOptional);
        System.out.println("demo.myInjectedUser = " + demo.myInjectedUser);

        // 期待输出 user superUser
        System.out.println("demo.users = " + demo.users);

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
}
