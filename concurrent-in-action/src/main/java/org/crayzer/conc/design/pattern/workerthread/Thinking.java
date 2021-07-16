package org.crayzer.conc.design.pattern.workerthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thinking {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newSingleThreadExecutor();
        // ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(() -> {
            try {
                String qq = pool.submit(() -> "QQ").get();
                System.out.println(qq);
            } catch (Exception e) {
            }
        });
    }
}
