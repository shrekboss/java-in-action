package org.crayzer.conc.juc.lock.stamplock;

import java.util.concurrent.locks.StampedLock;

/**
 * 
 */
public class StampedLockForOptimistic {

    final StampedLock sl = new StampedLock();

    /**
     * describe: StampedLock 读模板：
     **/
    public void methodForRead() {
        // 乐观读
        long stamp = sl.tryOptimisticRead();
        // 读入方法局部变量
        // ......
        // 校验stamp
        if (!sl.validate(stamp)) {
            // 升级为悲观读锁
            stamp = sl.readLock();
            try {
                // 读入方法局部变量
                // .....
            } finally {
                //释放悲观读锁
                sl.unlockRead(stamp);
            }
        }
        //使用方法局部变量执行业务操作
        // ......
    }

    /**
     * describe: StampedLock 写模板：
     **/
    public void methodForWrite() {
        long stamp = sl.writeLock();
        try {
            // 写共享变量
            //......
        } finally {
            sl.unlockWrite(stamp);
        }
    }
}
