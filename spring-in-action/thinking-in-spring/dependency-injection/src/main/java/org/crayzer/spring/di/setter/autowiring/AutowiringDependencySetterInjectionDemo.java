package org.crayzer.spring.di.setter.autowiring;

import org.crayzer.spring.di.UserHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * "byName" or "byType" Autowiring 依赖 Setter 方法注入示例
 *
 * @author Crayzer
 */
public class AutowiringDependencySetterInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        /**
         * autowiring=byName:
         * UserHolder{user=User{name='crayzer', id=1}}
         * autowiring=byType:
         * UserHolder{user=SuperUser{address='杭州'} User{name='crayzer', id=1}}
         */
        String xmlResourcePath = "classpath:/META-INF/autowiring-dependency-setter-injection.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        UserHolder user = beanFactory.getBean(UserHolder.class);
        System.out.println(user);
    }
}
