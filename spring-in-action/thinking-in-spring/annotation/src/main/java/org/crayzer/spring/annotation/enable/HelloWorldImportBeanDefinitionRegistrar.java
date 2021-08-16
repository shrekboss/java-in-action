package org.crayzer.spring.annotation.enable;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * "HelloWorld" 模块 {@link ImportBeanDefinitionRegistrar}
 */
public class HelloWorldImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        AnnotatedGenericBeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(HelloWorldConfiguration.class);
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
    }
}
