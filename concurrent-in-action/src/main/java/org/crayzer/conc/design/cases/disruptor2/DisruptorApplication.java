package org.crayzer.conc.design.cases.disruptor2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
public class DisruptorApplication {

    /**
     * 项目内部使用Disruptor做消息队列
     * @throws Exception
     */
    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DisruptorApplication.class).run(args);

        DisruptorMqService disruptorMqService = (DisruptorMqService) context.getBean("disruptorMqServiceImpl");
        disruptorMqService.sayHelloMq("消息到了，Hello world!");
        log.info("消息队列已发送完毕");
        //这里停止2000ms是为了确定是处理消息是异步的
        Thread.sleep(2000);
    }
}