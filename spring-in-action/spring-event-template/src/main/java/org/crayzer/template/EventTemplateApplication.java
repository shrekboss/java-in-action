package org.crayzer.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Event（Application Event）其实就是一个观察者设计模式，一个 Bean 处理完成任务后希望通知其它 Bean 或者说一个 Bean 想观察监听另一个Bean 的行为。
 */
@SpringBootApplication
public class EventTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventTemplateApplication.class, args);
    }
}
