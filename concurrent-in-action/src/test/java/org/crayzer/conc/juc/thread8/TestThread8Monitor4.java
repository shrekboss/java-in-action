package org.crayzer.conc.juc.thread8;

import org.junit.Test;

/**
 * 两个普通同步方法，两个 Number 对象，打印?  //two  one
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor4 {

    @Test
    public void thread8MonitorTest() {
        Number4 number = new Number4();
        Number4 number2 = new Number4();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number2.getTwo();
            }
        }).start();
    }
}

class Number4 {
    public synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }
}
