package org.crayzer.conc.juc.lock.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link ReentrantLock} 重入锁案例
 *
 * 当线程 T1 执行到 ① 处时，已经获取到了锁 rtl ，当在 ① 处调用 get() 方法时，
 * 会在 ② 再次对锁 rtl 执行加锁操作
 *
 * 可重入函数是线程安全的
 */
public class ReentrantLockExample {
    private final Lock lock = new ReentrantLock();
    private int value;

    public int get() {
        // 获取锁 ②
        lock.lock();

        try {
            return value;
        } finally {
            // 保证锁能释放
            lock.unlock();
        }
    }

    public void addOne() {
        lock.lock();

        try {
            // ①
            value = 1 + get();
        } finally {
            lock.unlock();
        }
    }
}
