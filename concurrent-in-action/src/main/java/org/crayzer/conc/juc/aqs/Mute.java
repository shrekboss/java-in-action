package org.crayzer.conc.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于 AQS 实现排它锁实例
 */
public class Mute implements Lock {

    /**
     * 静态内部类，自定义同步器
     */
    private final static class Sync extends AbstractQueuedSynchronizer {

        // 当状态为 0 的时候获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 释放锁，将状态设置为 0
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        // 是否处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        protected Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final static Sync SYNC = new Sync();

    @Override
    public void lock() {
        SYNC.acquire(1);
    }

    @Override
    public boolean tryLock() {
        return SYNC.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return SYNC.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        SYNC.acquireInterruptibly(1);
    }

    @Override
    public void unlock() {
        SYNC.release(1);
    }

    @Override
    public Condition newCondition() {
        return SYNC.newCondition();
    }

    public boolean isLock() {
        return SYNC.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return SYNC.hasQueuedThreads();
    }
}
