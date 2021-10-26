package org.crayzer.conc.jdkconcurrentutil.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写代码，使用3个线程，1个线程打印X，一个线程打印Y，一个线程打印Z，同时执行连续打印10次"XYZ"
 */
public class PrintStr implements Runnable {

    private final static int COUNT = 10;
    private Lock lock;
    private Condition thisCondition;
    private Condition nextCondition;
    private String printContext;

    public PrintStr(Lock lock, Condition thisCondition, Condition nextCondition, String printContext) {
        this.lock = lock;
        this.thisCondition = thisCondition;
        this.nextCondition = nextCondition;
        this.printContext = printContext;
    }

    @Override
    public void run() {
        lock.lock();

        try {
            for (int i = 0; i < COUNT; i++) {
                System.out.print(printContext);
                nextCondition.signal();

                if (i < COUNT - 1) {
                    this.thisCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition printXCondition = lock.newCondition();
        Condition printYCondition = lock.newCondition();
        Condition printZCondition = lock.newCondition();

        Thread printX = new Thread(new PrintStr(lock, printXCondition, printYCondition, "X"));
        Thread printY = new Thread(new PrintStr(lock, printYCondition, printZCondition, "Y"));
        Thread printZ = new Thread(new PrintStr(lock, printZCondition, printXCondition, "Z"));

        printX.start();
        Thread.sleep(100);
        printY.start();
        Thread.sleep(100);
        printZ.start();
        Thread.sleep(100);
    }
}
