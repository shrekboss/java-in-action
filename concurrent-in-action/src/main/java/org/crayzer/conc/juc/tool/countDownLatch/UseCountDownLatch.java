package org.crayzer.conc.juc.tool.countDownLatch;

import org.crayzer.conc.juc.SleepTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * {@link CountDownLatch} 俗称闭锁,作用类似加强版的Join,是让一组线程等待其他的线程完成工作以后才执行
 * <p/>传入的参数 count 可以大于等于
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class UseCountDownLatch {

    private static final Logger logger = LoggerFactory.getLogger(UseCountDownLatch.class);

    /**
     * 设置6个扣除点
     */
    static CountDownLatch countDownLatch = new CountDownLatch(6);

    public static void main(String[] args) {
        new Thread(() -> {
            SleepTools.ms(1);
            logger.info("thread_" + Thread.currentThread().getId() + " ready init work step 1st...");
            countDownLatch.countDown();
            logger.info("begin stop 2nd...");
            SleepTools.ms(1);
            logger.info("thread_" + Thread.currentThread().getId() + " ready init work step 2st...");
            countDownLatch.countDown();
        }).start();

        new Thread(new BizThread()).start();

        for (int i = 0; i <= 3; i++) {
            new Thread(new InitThread()).start();
        }

        try {
            countDownLatch.await();
            logger.info("Main do its work...");
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }

    private static class InitThread implements Runnable {
        @Override
        public void run() {
            logger.info("thread_" + Thread.currentThread().getId() + " ready init work...");
            countDownLatch.countDown();
            for (int i = 0; i < 2; i++) {
                logger.info("thread_" + Thread.currentThread().getId() + "...continue do its work");
            }
        }
    }

    private static class BizThread implements Runnable {
        @Override
        public void run() {
            try {
                countDownLatch.await();
                for (int i = 0; i < 3; i++) {
                    logger.info("bizThread_" + Thread.currentThread().getId() + " do business...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
