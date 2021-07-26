package org.crayzer.spring.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * ResolvableDependency 作为依赖来源
 *
 * @author Crayzer
 */
public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void init() {
        System.out.println(value);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ResolvableDependencySourceDemo.class);

        /*  所依赖的对象没有被依赖 refresh()#invokeBeanFactoryPostProcessors*/
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            // 注册 Resolvable Dependency
            beanFactory.registerResolvableDependency(String.class, "Hi, Crayzer");
        });
        applicationContext.refresh();

        /*  所依赖的对象没有被依赖 */
        // AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        // if (beanFactory instanceof ConfigurableListableBeanFactory) {
        //     ConfigurableListableBeanFactory configurableListableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
        //     // 注册 Resolvable Dependency
        //     configurableListableBeanFactory.registerResolvableDependency(String.class, "Hi, Crayzer");
        // }

        applicationContext.close();
    }
}
