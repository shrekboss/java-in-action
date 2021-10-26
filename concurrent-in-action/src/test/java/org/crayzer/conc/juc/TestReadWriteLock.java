package org.crayzer.conc.juc;

import org.junit.Test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * {@link ReadWriteLock} 读写锁，读和写之间不互斥，写和写之间互斥
 * <p>读锁是多个线程可以一起，写锁是独占的
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestReadWriteLock {

    @Test
    public void readWriteLockTest() {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.set((int) (Math.random() * 101));
            }
        }, "writeLock").start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.get();
                }
            }, "readLock-" + i).start();
        }
    }

}

class ReadWriteLockDemo {
    private int number = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读:" + number);
        } finally {
            lock.readLock().unlock();
        }
    }

    //写
    public void set(int number) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写:" + number);
            this.number = number;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
