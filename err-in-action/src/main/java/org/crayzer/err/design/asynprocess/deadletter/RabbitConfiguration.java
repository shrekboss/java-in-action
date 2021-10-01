package org.crayzer.err.design.asynprocess.deadletter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
@Slf4j
public class RabbitConfiguration {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Declarables declarables() {
        Queue queue = new Queue(Consts.QUEUE);
        DirectExchange directExchange = new DirectExchange(Consts.EXCHANGE);
        return new Declarables(queue, directExchange,
                BindingBuilder.bind(queue).to(directExchange).with(Consts.ROUTING_KEY));
    }


    //定义死信交换器和队列，并且进行绑定
    @Bean
    public Declarables declarablesForDead() {
        Queue queue = new Queue(Consts.DEAD_QUEUE);
        DirectExchange directExchange = new DirectExchange(Consts.DEAD_EXCHANGE);
        return new Declarables(queue, directExchange,
                BindingBuilder.bind(queue).to(directExchange).with(Consts.DEAD_ROUTING_KEY));
    }

    //定义重试操作拦截器
    @Bean
    public RetryOperationsInterceptor interceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(5) //最多尝试 5 次（重试 4 次）
                .backOffOptions(1000, 2.0, 10000) //指数退避重试
                .recoverer(new RepublishMessageRecoverer(rabbitTemplate, Consts.DEAD_EXCHANGE, Consts.DEAD_ROUTING_KEY)) //重新投递重试达到上限的消息
                .build();
    }

    //通过定义SimpleRabbitListenerContainerFactory，设置其adviceChain属性为之前定义的RetryOperationsInterceptor来启用重试拦截器
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(interceptor());
        return factory;
    }
}
