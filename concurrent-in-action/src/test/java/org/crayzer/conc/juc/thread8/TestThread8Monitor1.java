package org.crayzer.conc.juc.thread8;

import org.junit.Test;

/**
 * 两个普通同步方法，两个线程，标准打印， 打印? //one  two
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor1 {
    @Test
    public void thread8MonitorTest() {
        Number number = new Number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getTwo();
            }
        }).start();
    }
}

class Number {
    public synchronized void getOne() {
        System.out.println("one");
    }
    public synchronized void getTwo() {
        System.out.println("two");
    }
}
