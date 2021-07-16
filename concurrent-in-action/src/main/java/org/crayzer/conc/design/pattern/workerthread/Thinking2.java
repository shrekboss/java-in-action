package org.crayzer.conc.design.pattern.workerthread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thinking2 {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        CountDownLatch l1 = new CountDownLatch(2);
        for (int i = 0; i < 2; i++) {
            System.out.println("L1");
            es.execute(() -> {
                CountDownLatch l2 = new CountDownLatch(2);
                for (int j = 0; j < 2; j++) {
                    es.execute(() -> {
                        System.out.println("L2");
                        l2.countDown();
                    });
                }
                try {
                    l2.await();
                    l1.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            try {
                l1.await();
                System.out.println("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
