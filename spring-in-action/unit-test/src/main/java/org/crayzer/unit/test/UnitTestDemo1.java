package org.crayzer.unit.test;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <a href="https://mp.weixin.qq.com/s/ahLjLKmxk5bPZ_Zt52Zc-g">代码重构：面向单元测试</a>
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 **/
public class UnitTestDemo1 {
    public static void main(String[] args) {
        new UnitTestDemo1().producerConsumer();
    }


    public void producerConsumer() {
        new ProducerConsumer<Integer>(Executors.newFixedThreadPool(2)) {
            @Override
            void produce() {
                for (int i = 0; i < 10; i++) {
                    produceInner(i + ThreadLocalRandom.current().nextInt(100));
                }
            }

            @Override
            void consume() {
                while (true) {
                    Integer result = consumeInner();
                    System.out.println(result);
                }
            }
        }.start();
    }

    public <T> void producerConsumerInner(Executor executor, Consumer<Consumer<T>> producer, Consumer<Supplier<T>> consumer) {
        BlockingQueue<T> blockingQueue = new LinkedBlockingQueue<>();
        executor.execute(() -> producer.accept(blockingQueue::add));
        executor.execute(() -> consumer.accept(() -> {
            try {
                return blockingQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Test
    public void testProducerConsumerInner() {
        producerConsumerInner(Runnable::run, (Consumer<Consumer<Integer>>) producer -> {
            producer.accept(1);
            producer.accept(2);
        }, consumer -> {
            assert consumer.get() == 1;
            assert consumer.get() == 2;
        });
    }

    @Test
    public void testProducerConsumerAbCls() {
        new ProducerConsumer<Integer>(Runnable::run) {
            @Override
            void produce() {
                produceInner(1);
                produceInner(2);
            }

            @Override
            void consume() {
                assert consumeInner() == 1;
                assert consumeInner() == 2;
            }
        }.start();
    }
}
