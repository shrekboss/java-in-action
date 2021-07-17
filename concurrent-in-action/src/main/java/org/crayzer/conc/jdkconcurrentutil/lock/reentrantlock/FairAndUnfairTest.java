package org.crayzer.conc.jdkconcurrentutil.lock.reentrantlock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnfairTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        testLock(fairLock);
    }

    @Test
    public void unFair() {
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        for (int i = 0; i < 5; i++) {
            Thread t = new Job(lock);
            t.setName("" + i);
            t.start();
        }
        countDownLatch.countDown();
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
            }

            for (int i = 0; i < 2; i++) {
                lock.lock();

                try {
                    System.out.println("Lock by[ " + getName() + " ], waiting by " + ((ReentrantLock2)lock).getQueuedThreads());
                } finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    private static class ReentrantLock2 extends ReentrantLock{
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
