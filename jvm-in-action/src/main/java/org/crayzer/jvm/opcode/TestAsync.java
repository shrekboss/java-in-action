package org.crayzer.jvm.opcode;

import java.util.concurrent.*;
public class TestAsync implements Runnable {
    private CyclicBarrier barrier;
    public TestAsync(CyclicBarrier barrier) {
        this.barrier = barrier;
    }
    public void run() {
        try {
            System.out.println("开始执行任务:" + Thread.currentThread().getName());
            this.barrier.await(); // 等待其他人对齐
            System.out.println("任务执行完成:" + Thread.currentThread().getName());
        } catch (Exception nunimportant) {}
    }
    public static void main(String[] args) throws InterruptedException {
        int processorNum = Runtime.getRuntime().availableProcessors();
        int taskNum = processorNum + 1;
        CyclicBarrier barrier = new CyclicBarrier(taskNum);
        //
        for (int i = 0; i < processorNum; i++) {
            CompletableFuture.runAsync(new TestAsync(barrier));
        }
        TimeUnit.SECONDS.sleep(10);
    }
}
