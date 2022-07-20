package org.crayzer.err.coding.timerperformance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class TimePerformance {

    public static final int LOOP_COUNT = 9999999;
    public static final int THREAD_COUNT = 30;

    public static void main(String[] args) {

        Runnable millisTest = () -> {

            long start = System.currentTimeMillis();
            for (int i = 0; i < LOOP_COUNT; i++) {
                System.currentTimeMillis();
            }
            long end = System.currentTimeMillis();

            System.out.printf("%s : %f ns per call\n",
                    Thread.currentThread().getName(), ((double)end - start) * 1000000 / LOOP_COUNT);
        };

        Runnable nanoTest = () -> {

            long start = System.currentTimeMillis();
            for (int i = 0; i < LOOP_COUNT; i++) {
                System.nanoTime();
            }
            long end = System.currentTimeMillis();

            System.out.printf("%s : %f ns per call\n",
                    Thread.currentThread().getName(), ((double)end - start) * 1000000 / LOOP_COUNT);
        };

        Consumer<Runnable> testing = test -> {
            System.out.println("Single thread test:");
            test.run();

            System.out.println(THREAD_COUNT + " threads test:");
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < THREAD_COUNT; i++) {
                Thread t = new Thread(test);
                t.start();
                threads.add(t);
            }
            // Wait for all threads to finish
            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        };

        System.out.println("//// Test System.nanoTime()");
        testing.accept(nanoTest);
        System.out.println("//// Test System.currentTimeMillis()");
        testing.accept(millisTest);
    }
}
