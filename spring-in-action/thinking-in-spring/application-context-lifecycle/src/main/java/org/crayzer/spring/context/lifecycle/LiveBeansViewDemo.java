package org.crayzer.spring.context.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.LiveBeansView;

import java.io.IOException;

import static org.springframework.context.support.LiveBeansView.MBEAN_DOMAIN_PROPERTY_NAME;

/**
 * {@link LiveBeansView} 示例
 *
 * @see LiveBeansView
 */
public class LiveBeansViewDemo {

    public static void main(String[] args) throws IOException {

        // 添加 LiveBeansView 的 ObjectName 的 domain
        System.setProperty(MBEAN_DOMAIN_PROPERTY_NAME, "org.crayzer.context.lifecycle");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 注册 Configuration Class
        context.register(LiveBeansViewDemo.class);

        // 启动 Spring 应用上下文
        context.refresh();

        System.out.println("按任意键继续...");
        System.in.read();

        context.close();
        // 关闭 Spring 应用上下文
    }

    /**
     * [ { "context": "org.springframework.context.annotation.AnnotationConfigApplicationContext@2f7a2457", "parent": null, "beans": [ { "bean": "liveBeansViewDemo", "aliases": [], "scope": "singleton", "type": "org.crayzer.context.lifecycle.LiveBeansViewDemo", "resource": "null", "dependencies": [] }] }]
     */
}
