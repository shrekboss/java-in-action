package org.crayzer.spring.bean.definition;

import org.crayzer.spring.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * BeanDefinition 两种创建方式
 * <p>
 * 1. BeanDefinitionBuilder
 * <p>
 * 2. AbstractBeanDefinition
 *
 * @author Crayzer
 */
public class BeanDefinitionCreationDemo {
    public static void main(String[] args) {
        // 1. 通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("id", 1L)
                .addPropertyValue("name", "crayzer");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // beanDefinition 并非是 Bean 的终态，可以自定义修改
        System.out.println(beanDefinition);

        // 2. 通过 AbstractBeanDefinition 构建
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues
                .add("id", 2L)
                .add("name", "马克图布");
        genericBeanDefinition.setPropertyValues(mutablePropertyValues);
        System.out.println(genericBeanDefinition);
    }
}
