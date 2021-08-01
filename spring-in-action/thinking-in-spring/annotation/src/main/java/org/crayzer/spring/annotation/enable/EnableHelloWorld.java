package org.crayzer.spring.annotation.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 激活 "HelloWorld" 模块注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 第二步：通过 @Import 注解导入具体实现
//@Import(HelloWorldConfiguration.class) // 方法一： 通过 Configuration Class 实现
//@Import(HelloWorldImportSelector.class)// 方法二：通过 ImportSelector 接口实现
@Import(HelloWorldImportBeanDefinitionRegistrar.class)// 方法三：通过 ImportBeanDefinitionRegistrar
public @interface EnableHelloWorld { // 第一步：通过 @EnableXXX 命名
}
