package org.crayzer.spring.configuration.metadata;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ObjectUtils;

/**
 * Bean 配置元信息示例
 *
 * @author crayzer
 */
public class BeanConfigurationMetadataDemo {

    public static void main(String[] args) {
        // BeanDefinition 的定义(声明)
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name", "crayzer");
        // 获取 AbstractBeanDefinition
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        // 附加属性(不影响 Bean 的实例化、属性赋值、初始化)
        beanDefinition.setAttribute("name", "马克图布");
        // 当前 BeanDefinition 来之何方(辅助作用)
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
                    BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
                    if (BeanConfigurationMetadataDemo.class.equals(bd.getSource())) {
                        // 属性(存储)上下文
                        String name = (String) bd.getAttribute("name"); // "马克图布"
                        User user = (User) bean;
                        user.setName(name);
                    }
                }
                return bean;
            }
        });
        // 注册 User 的 BeanDefinition
        beanFactory.registerBeanDefinition("user", beanDefinition);

        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }
}
