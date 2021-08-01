package org.crayzer.spring.event.custom;

import org.springframework.context.ApplicationListener;

/**
 * 自定义 Spring 事件监听器
 *
 * @author crayzer
 */
public class MySpringEventListener implements ApplicationListener<MySpringEvent> {

    @Override
    public void onApplicationEvent(MySpringEvent event) {
        System.out.printf("[线程 ： %s] 监听到事件 : %s\n", Thread.currentThread().getName(), event);
    }
}
