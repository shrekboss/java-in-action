package org.crayzer.conc.jdkconcurrentutil.tool.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * new CyclicBarrier(3);
 * 主线程和子线程永远等待
 */
public class CyclicBarrierTest2 {

    // 优先执行 barrierAction -> new A()
    static CyclicBarrier c = new CyclicBarrier(2, new A());

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                c.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(1);
        }).start();

        try {
            c.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }

    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println(3);
        }
    }
}
