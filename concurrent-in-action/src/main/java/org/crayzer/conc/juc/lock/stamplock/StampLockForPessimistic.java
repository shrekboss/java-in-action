package org.crayzer.conc.jdkconcurrentutil.lock.stamplock;

import java.util.concurrent.locks.StampedLock;

/**
 * {@link StampedLock}
 *
 * 获取/释放悲观读锁示意代码
 */
public class StampLockForPessimistic {
    public static void main(String[] args) {

        final StampedLock sl = new StampedLock();

        long stamp = sl.readLock();
        try {
            //省略业务相关代码
        } finally {
            sl.unlockRead(stamp);
        }

        // 获取/释放写锁示意代码
        long stamp1 = sl.writeLock();
        try {
            //省略业务相关代码
        } finally {
            sl.unlockWrite(stamp1);
        }
    }
}
