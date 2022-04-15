package org.crayzer.conc.juc.tool.countDownLatch;

import org.crayzer.conc.juc.SleepTools;

import java.util.concurrent.CountDownLatch;

/**
 * TODO：简单描述一嘴
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class UseCountDownLatch {

    /**
     * 设置6个扣除点
     */
    static CountDownLatch countDownLatch = new CountDownLatch(6);

    public static void main(String[] args) {
        new Thread() {

            @Override
            public void run() {
                SleepTools.ms(1);
                System.out.println("thread_" + Thread.currentThread().getId() + " ready init work step 1st...");
                countDownLatch.countDown();
                System.out.println("begin stop 2nd...");
                SleepTools.ms(1);
                System.out.println("thread_" + Thread.currentThread().getId() + " ready init work step 2st...");
                countDownLatch.countDown();
            }
        }.start();

        new Thread(new BizThread()).start();

        for (int i = 0; i <= 3; i++) {
            new Thread(new InitThread()).start();
        }

        try {
            countDownLatch.await();
            System.out.println("Main do its work...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class InitThread implements Runnable {
        @Override
        public void run() {
            System.out.println("thread_" + Thread.currentThread().getId() + " ready init work...");
            countDownLatch.countDown();
            for (int i = 0; i < 2; i++) {
                System.out.println("thread_" + Thread.currentThread().getId() + "...continue do its work");
            }
        }
    }

    private static class BizThread implements Runnable {
        @Override
        public void run() {
            try {
                countDownLatch.await();
                for (int i = 0; i < 3; i++) {
                    System.out.println("bizThread_" + Thread.currentThread().getId() + " do business...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
