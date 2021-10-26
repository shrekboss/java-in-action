package org.crayzer.conc.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockCounter {

    private int sum = 0;
    private Lock lock = new ReentrantLock(true);

    public int incAndGet() {

        try {
            lock.lock();
            return ++sum;
        } finally {
            lock.unlock();
        }
    }

    public int getSum() {
        return sum;
    }

    public static void main(String[] args) {
        int loopNum = 100_0000;
        LockCounter counter = new LockCounter();
        IntStream.range(0, loopNum).parallel().forEach(i -> counter.incAndGet());

        System.out.println(counter.getSum());
    }
}
