package org.crayzer.err.design.asynprocess.fanoutvswork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@Slf4j
@Configuration
@RestController
@RequestMapping("fanoutwrong")
public class FanoutQueueWrong {
    private static final String QUEUE = "newuser";
    private static final String EXCHANGE = "newuser";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public void sendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE, "", UUID.randomUUID().toString());
    }
    //声明FanoutExchange，然后绑定到队列，FanoutExchange绑定队列的时候不需要routingKey

    // 错误原因：广播交换器非常简单，它会忽略 routingKey，广播消息到所有绑定的队列。在这个案例中，
    // 两个会员服务和两个营销服务都绑定了同一个队列，所以这四个服务只能收到一次消息
    @Bean
    public Declarables declarablesFromFanoutWrong() {
        Queue queue = new Queue(QUEUE);
        FanoutExchange exchange = new FanoutExchange(EXCHANGE);
        return new Declarables(queue, exchange,
                BindingBuilder.bind(queue).to(exchange));
    }
    //会员服务实例1
    @RabbitListener(queues = QUEUE) // todo
    public void memberService1(String userName) {
        log.info("memberService1: welcome message sent to new user {}", userName);

    }
    //会员服务实例2
    @RabbitListener(queues = QUEUE) // todo
    public void memberService2(String userName) {
        log.info("memberService2: welcome message sent to new user {}", userName);

    }
    //营销服务实例1
    @RabbitListener(queues = QUEUE) // todo
    public void promotionService1(String userName) {
        log.info("promotionService1: gift sent to new user {}", userName);
    }
    //营销服务实例2
    @RabbitListener(queues = QUEUE) // todo
    public void promotionService2(String userName) {
        log.info("promotionService2: gift sent to new user {}", userName);
    }
}
