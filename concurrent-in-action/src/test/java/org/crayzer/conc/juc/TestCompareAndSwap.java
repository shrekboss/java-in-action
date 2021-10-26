package org.crayzer.conc.juc;

import org.junit.Test;

/**
 * <p> CAS 模拟，模拟带锁，底层是不是 synchronized
 * <p> CAS 每次修改之前，都会执行获取比较操作
 * <p> CAS 比较失败的时候不会放弃CPU，会反复执行，直到自己修改主内存的数据
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestCompareAndSwap {

    @Test
    public void compareAndSwapTest() {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int expectValue = cas.getValue();
                System.out.println(cas.compareAndSet(expectValue, (int) Math.random() * 101));
            }).start();
        }
    }
}

class CompareAndSwap {
    public int value;

    // 获取内存值
    public synchronized int getValue() {
        return value;
    }

    // 比较并交换
    public synchronized int compareAndSwap(int expectValue, int newV) {
        int oldV = value;
        // 内存值和预估值一直，就替换
        if (oldV == expectValue) {
            this.value = newV;
        }
        return oldV;
    }

    // 设置，调用比较交换，看期望值和原来的值是否一致
    public synchronized boolean compareAndSet(int expectValue, int newV) {
        return expectValue == compareAndSwap(expectValue, newV);
    }
}