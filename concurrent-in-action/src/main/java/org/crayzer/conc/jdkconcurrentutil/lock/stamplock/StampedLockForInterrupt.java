package org.crayzer.conc.jdkconcurrentutil.lock.stamplock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * {@link StampedLock}
 *
 * 使用 StampedLock 一定不要调用中断操作，如果需要支持中断功能，
 * 一定使用可中断的悲观读锁 readLockInterruptibly() 和写锁 writeLockInterruptibly()
 */
public class StampedLockForInterrupt {

    public static void main(String[] args) throws InterruptedException {

        final StampedLock lock = new StampedLock();
        Thread T1 = new Thread(() -> {
            // 获取写锁
            lock.writeLock();
            // 永远阻塞在此处，不释放写锁
            LockSupport.park();
        });
        T1.start();
        // 保证T1获取写锁
        Thread.sleep(100);
        Thread T2 = new Thread(() ->
                //阻塞在悲观读锁
                lock.readLock()
        );
        T2.start();
        // 保证T2阻塞在读锁
        Thread.sleep(100);
        //中断线程T2
        //会导致线程T2所在CPU飙升
        T2.interrupt();
        T2.join();
    }
}
