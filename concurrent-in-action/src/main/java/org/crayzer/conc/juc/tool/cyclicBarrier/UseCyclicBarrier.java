package org.crayzer.conc.juc.tool.cyclicBarrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * {@link CyclicBarrier} 俗称栅栏锁,作用是让一组线程到达某个屏障,被阻塞,一直到组内的最后一个线程到达,然后屏障开放,所有线程继续运行
 * <p/>传入的参数 parties 需要和线程数量吻合
 * <p/>传入的参数 barrierAction 当屏障开放后,执行的任务线程
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class UseCyclicBarrier {

    private static final Logger logger = LoggerFactory.getLogger(UseCyclicBarrier.class);

    private static ConcurrentHashMap<String, Long> resultMap = new ConcurrentHashMap<>();

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new CollectThread());

    public static void main(String[] args) {
        for (int i = 0; i <= 4; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }

    private static class CollectThread implements Runnable {
        @Override
        public void run() {
            StringBuffer result = new StringBuffer();

            for (Map.Entry<String, Long> workResult : resultMap.entrySet()) {
                result.append("[" + workResult.getValue() + "]");
            }

            logger.info("the result = " + result);
            logger.info("do other business...");
        }
    }

    private static class SubThread implements Runnable {

        @Override
        public void run() {
            long id = Thread.currentThread().getId();

            resultMap.put(String.valueOf(id), id);

            Random random = new Random();

            try {
                if (random.nextBoolean()) {
                    Thread.sleep(1000 + id);
                    logger.info("Thread_" + id + "...do something");
                }
                logger.info(id + " is await");
                cyclicBarrier.await();
                Thread.sleep(1000 + id);
                logger.info("Thread_" + id + "...do its business");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
