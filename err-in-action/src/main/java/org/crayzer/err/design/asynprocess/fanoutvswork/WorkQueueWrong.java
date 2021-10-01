package org.crayzer.err.design.asynprocess.fanoutvswork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
 @Configuration
 @RestController
@RequestMapping("workqueuewrong")
public class WorkQueueWrong {

    private static final String EXCHANGE = "newuserExchange";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * curl http://localhost:45678/workqueuewrong
     * curl http://localhost:12345/workqueuewrong
     */
    @GetMapping
    public void sendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE, "wrong", UUID.randomUUID().toString());
    }

    //使用匿名队列作为消息队列
//    @Bean
//    public Queue queue() {
//        // RabbitMQ 的直接交换器根据 routingKey 对消息进行路由。由于程序每次启动都会创建匿名（随机命名）的
//        // 队列，所以相当于每一个会员服务实例都对应独立的队列，以空 routingKey 绑定到直接交换器。用户服务发
//        // 出消息的时候也设置了 routingKey 为空，所以直接交换器收到消息之后，发现有两条队列匹配，于是都转发
//        // 了消息
//        return new AnonymousQueue();
//    }
//
//    //声明DirectExchange交换器，绑定队列到交换器
//    @Bean
//    public Declarables declarablesWorkqueueWrong() {
//        DirectExchange exchange = new DirectExchange(EXCHANGE);
//        return new Declarables(queue(), exchange, BindingBuilder.bind(queue()).to(exchange).with("wrong"));
//    }
//
//    //监听队列，队列名称直接通过SpEL表达式引用Bean
//    @RabbitListener(queues = "#{queue.name}")
//    public void memberService(String userName) {
//        log.info("memberService: welcome message sent to new user {} from {}",
//                userName, System.getProperty("server.port"));
//    }
}
