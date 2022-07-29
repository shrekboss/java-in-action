package org.crayzer.unit.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <a href="https://mp.weixin.qq.com/s/ahLjLKmxk5bPZ_Zt52Zc-g">代码重构：面向单元测试</a>
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 **/
public class UnitTestDemo2 {
    public static void main(String[] args) {
        new UnitTestDemo2().producerConsumer();
    }

    public void producerConsumer() {
        new NumberProducerConsumer(Executors.newFixedThreadPool(2),
                () -> ThreadLocalRandom.current().nextInt(100),
                System.out::println).start();
    }
}
