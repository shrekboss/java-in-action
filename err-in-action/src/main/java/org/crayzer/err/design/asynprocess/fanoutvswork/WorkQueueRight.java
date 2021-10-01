package org.crayzer.err.design.asynprocess.fanoutvswork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
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
@RequestMapping("workqueueright")
public class WorkQueueRight {

    private static final String EXCHANGE = "newuserExchange";
    private static final String QUEUE = "newuserQueue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * curl http://localhost:45678/workqueueright
     * curl http://localhost:12345/workqueueright
     */
    @GetMapping
    public void sendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE, "right", UUID.randomUUID().toString());
    }

    @Bean
    public Queue queue() {
        // return new AnonymousQueue();
        return new Queue(QUEUE);
    }

    @Bean
    public Declarables declarablesFromWorkqueueRight() {
        DirectExchange exchange = new DirectExchange(EXCHANGE);
        return new Declarables(queue(), exchange, BindingBuilder.bind(queue()).to(exchange).with("right"));
    }

    @RabbitListener(queues = "#{queue.name}")
    public void memberService(String userName) {
        log.info("memberService: welcome message sent to new user {} from {}",
                userName, System.getProperty("server.port"));
    }
}
