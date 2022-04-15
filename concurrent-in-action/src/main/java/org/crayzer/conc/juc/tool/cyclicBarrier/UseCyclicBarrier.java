package org.crayzer.conc.juc.tool.cyclicBarrier;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class UseCyclicBarrier {

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

            System.out.println("the result = " + result);
            System.out.println("do other business...");
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
                    System.out.println("Thread_" + id + "...do something");
                }
                System.out.println(id + " is await");
                cyclicBarrier.await();
                Thread.sleep(1000 + id);
                System.out.println("Thread_" + id + "...do its business");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
