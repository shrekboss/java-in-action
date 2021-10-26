package org.crayzer.conc.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ReentrantReadWriteLockCounter {

    private int sum = 0;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public int incAndGet() {
        try {
            lock.writeLock().lock();
            return ++sum;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getSum() {
        try {
            lock.readLock().lock();
            return sum;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        int loopNum = 100_0000;
        LockCounter counter = new LockCounter();
        IntStream.range(0, loopNum).parallel().forEach(i -> counter.incAndGet());
        System.out.println(counter.getSum());
    }
}
