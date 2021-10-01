package org.crayzer.err.design.asynprocess.deadletter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQListener {
    @RabbitListener(queues = Consts.QUEUE)
    public void handler(String data) {
        //http://localhost:15672/#/
        log.info("got message {}", data);
        throw new NullPointerException("error");
        // 解决死信无限重复进入队列最简单的方式是，在程序处理出错的时候，
        // 直接抛出 AmqpRejectAndDontRequeueException 异常，避免消息重新进入队列
        //throw new AmqpRejectAndDontRequeueException("error");
    }

    // //死信队列处理程序
    @RabbitListener(queues = Consts.DEAD_QUEUE)
    public void deadHandler(String data) {
        log.error("got dead message {}", data);
    }
}
