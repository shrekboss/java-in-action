package org.crayzer.conc.jdkconcurrentutil.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * new CyclicBarrier(3);
 * 主线程和子线程永远等待
 */
public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(3);

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
}
