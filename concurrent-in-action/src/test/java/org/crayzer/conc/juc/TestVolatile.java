package org.crayzer.conc.juc;

import org.junit.Test;

/**
 * Thread 已经修改了flag，但是main线程还是拿到的 false
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestVolatile {

    @Test
    public void wrongTestCase() {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            if (td.isFlag()) {
                System.out.println("______________");
                break;
            }
        }
    }

    @Test
    public void rightTestCase() {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            synchronized (td) {
                if (td.isFlag()) {
                    System.out.println("______________");
                    break;
                }
            }
        }
    }
}

class ThreadDemo implements Runnable {

    private boolean flag = false;

    /**
     * <p>volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。相较于 synchronized 是一种较为轻量级的同步策略。
     *
     * <p>注意：
     * <p>volatile 不具备“互斥性”
     * <p>volatile 不能保证变量的“原子性”
     */
    // private volatile boolean flag = false;
    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            // 增加这种出现问题的概率
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        flag = true;
        System.out.println(Thread.currentThread().getName() + "flag = " + isFlag());
    }
}
