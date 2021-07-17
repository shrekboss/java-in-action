package org.crayzer.conc.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadMain {

    public static void main(String[] args) {

        java0.conc0301.ThreadA threadA = new java0.conc0301.ThreadA();
        threadA.start();
        System.out.println("这是主线程：");

        java0.conc0301.ThreadB threadB = new java0.conc0301.ThreadB();
        new Thread(threadB).start();
        System.out.println("这是主线程：");

        ThreadC threadC = new ThreadC(); 
        FutureTask<String> futureTask = new FutureTask<>(threadC);
        new Thread(futureTask).start();
        System.out.println("这是主线程:begin!");
        try {
            System.out.println("得到的返回结果是:" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
