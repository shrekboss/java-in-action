package org.crayzer.spring.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * {@link Component} 扫描示例
 *
 * @see Component
 * @see ComponentScan
 * @since
 */
// basePackages() @AliasFor value()
// value() @AliasFor basePackages()
//@MyComponentScan2(basePackages = "org.crayzer.spring.annotation") // 指定 Class-Path(s)
@MyComponentScan(scanBasePackages = "org.crayzer.spring.annotation") // 指定 Class-Path(s)
//@ComponentScan(value = "org.crayzer.spring.annotation") // 指定 Class-Path(s)
public class ComponentScanDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class
        context.register(ComponentScanDemo.class);

        // 启动 Spring 应用上下文
        context.refresh();

        // 依赖查找 TestClass Bean
        // TestClass 标注 @MyComponent2
        // @MyComponent2 <- @MyComponent <- @Component
        // 从 Spring 4.0 开始支持多层次 @Component "派生"
        TestClass testClass = context.getBean(TestClass.class);

        // Annotation -> AnnotationAttributes(Map)

        System.out.println(testClass);

        // 关闭 Spring 应用上下文
        context.close();
    }
}
