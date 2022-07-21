package org.crayzer.template.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crayzer.template.event.MsgEvent;
import org.crayzer.template.event.OrderProductEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * TODO：简单描述一嘴
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    /** 注入ApplicationContext用来发布事件 */
    private final ApplicationContext applicationContext;

    /**
     * 下单
     *
     * @param orderId 订单ID
     */
    public String buyOrder(String orderId) {
        long start = System.currentTimeMillis();
        // 1.查询订单详情
        log.info("1.查询订单详情");

        // 2.检验订单价格 （同步处理）
        log.info("2.检验订单价格 （同步处理）");
        applicationContext.publishEvent(new OrderProductEvent(this, orderId));

        // 3.短信通知（异步处理）
        log.info("3.短信通知（异步处理）");
        applicationContext.publishEvent(new MsgEvent(orderId));

        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时：({})毫秒", end - start);
        return "购买成功";
    }
}
