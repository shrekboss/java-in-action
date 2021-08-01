package org.crayzer.spring.event;

import org.crayzer.spring.event.custom.MySpringEvent;
import org.crayzer.spring.event.custom.MySpringEvent2;
import org.crayzer.spring.event.custom.MySpringEventListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 自定义 Spring 事件示例
 *
 * @author crayzer
 */
public class CustomizedSpringEventDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        // 1.添加自定义 Spring 事件监听器
        // ListenerRetriever -> 0 .. N 个 ApplicationListener<MySpringEvent> 实例
        // MySpringEvent 以及它子孙类
        /**
         * 这种方式只能监听到 MySpringEvent 以及它子类的事件
         *
         * [线程 ： main] 监听到事件 : org.crayzer.spring.event.custom.MySpringEvent[source=Hello,World]
         * [线程 ： main] 监听到事件 : org.crayzer.spring.event.custom.MySpringEvent2[source=2021]
         * */
        context.addApplicationListener(new MySpringEventListener());

        /**
         * 这种方式能监听到所有的事件(事件类型扩大化)
         *
         * Event : org.springframework.context.event.ContextRefreshedEvent[...]
         * Event : org.crayzer.spring.event.custom.MySpringEvent[source=Hello,World]
         * Event : org.crayzer.spring.event.custom.MySpringEvent2[source=2021]
         * Event : org.springframework.context.event.ContextClosedEvent[...]
         * */
        context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("Event : " + event);
            }
        });

        // 2.启动 Spring 应用上下文
        context.refresh();

        // 3. 发布自定义 Spring 事件
        // ListenerCacheKey -> MySpringEvent
        context.publishEvent(new MySpringEvent("Hello,World"));
        context.publishEvent(new MySpringEvent2("2021"));

        // 4. 关闭 Spring 应用上下文
        context.close();
    }
}
