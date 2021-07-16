package org.crayzer.conc.jdkconcurrentutil.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有界队列实现
 * {@link Condition}
 */
public class BoundedQueue<T> {
    private Object[] items;
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BoundedQueue(int size) {
        this.items = new Object[size];
    }

    public void add(T t) throws InterruptedException {
        lock.lock();

        try {
            while (isFull()) {
                notFull.await();
            }
            items[addIndex] = t;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException {
        lock.lock();

        try {
            while (isEmpty()) {
                notEmpty.await();
            }
            Object x = items[removeIndex];
            if (++removeIndex == items.length) removeIndex = 0;
            notFull.signal();
            return (T) x;
        } finally {
            lock.unlock();
        }
    }

    private boolean isFull() {
        return count == items.length;
    }

    private boolean isEmpty() {
        return count == 0;
    }
}
