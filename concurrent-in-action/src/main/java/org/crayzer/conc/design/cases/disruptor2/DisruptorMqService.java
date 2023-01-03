package org.crayzer.conc.design.cases.disruptor2;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public interface DisruptorMqService {

    /**
     * 消息
     * @param message
     */
    void sayHelloMq(String message);
}
