package org.crayzer.conc.juc;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 闭锁
 * <p>CountDownLatch 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 *
 * <p>闭锁可以延迟线程的进度直到其到达终止状态，闭锁可以用来确保某些活动直到其他活动都完成才继续执行：
 *
 * <li>确保某个计算在其需要的所有资源都被初始化之后才继续执行;
 * <li>确保某个服务在其依赖的所有其他服务都已经启动之后才启动;
 * <li>等待直到某个操作所有参与者都准备就绪再继续执行。
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestCountDownLatch {

    @Test
    public void countDownLatchTest() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(5);
        LatchDemo ld = new LatchDemo(latch);
        //计算执行时间
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
        }
        //闭锁等待其他线程的执行
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("执行时间===============================" + (end - start));
    }
}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 1000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + "-------------" + i);
                    }
                }
            } finally {
                // 线程执行完毕后 countdown 减一
                latch.countDown();
            }
        }
    }
}

