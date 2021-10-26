package org.crayzer.conc.juc;

import org.junit.Test;

/**
 * <p>原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量
 *
 * <p> 1. volatile 保证内存可见性
 * <p> 2. CAS(Compare-And-Swap) 算法保证数据变量的原子性
 * <li> CAS 算法是硬件对于并发操作的支持
 * <li> CAS 包含了三个操作，内存值 V，预估值 A，更新至 B，当且金丹 V == A 时，V == B；否则，不会执行任何操作
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestAtomicDemo {

    @Test
    public void atomicTest() {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable {

    private int serialNumber = 0;
//    private AtomicInteger serialNumber = new AtomicInteger(0);

    public int getSerialNumber() {
        return serialNumber++;
//        return serialNumber.getAndIncrement();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }
}
