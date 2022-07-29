package org.crayzer.unit.test;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * <a href="https://mp.weixin.qq.com/s/ahLjLKmxk5bPZ_Zt52Zc-g">代码重构：面向单元测试</a>
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 **/
public class UnitTestDemo {

    public static void main(String[] args) {
        new UnitTestDemo().producerConsumer();
    }


    public void producerConsumer() {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
        Thread producerThread  = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                blockingQueue.add(i + ThreadLocalRandom.current().nextInt(100));
            }
        });
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    Integer result = blockingQueue.take();
                    System.out.println(result);
                }
            } catch (InterruptedException ignore) {
            }
        });
        producerThread.start();
        consumerThread.start();
    }

    public int f() {
        return ThreadLocalRandom.current().nextInt(100) + 1;
    }

    public Supplier<Integer> g(Supplier<Integer> integerSupplier) {
        return () -> integerSupplier.get() + 1;
    }

    public int g2(Supplier<Integer> integerSupplier) {
        return integerSupplier.get() + 1;
    }

    @Test
    public void testG() {
        Supplier<Integer> result = g(() -> 1);
        assert result.get() == 2;
    }
}
