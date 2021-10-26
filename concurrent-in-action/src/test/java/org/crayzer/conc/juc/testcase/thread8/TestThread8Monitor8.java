package org.crayzer.conc.juc.testcase.thread8;

import org.junit.Test;

/**
 * 两个静态同步方法，两个 Number 对象?   //one  two
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor8 {

    @Test
    public void thread8MonitorTest() {
        Number8 number = new Number8();
        Number8 number2 = new Number8();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }//这样其实不能通过类的实例访问静态，为演示这个问题
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number2.getTwo();
            }
        }).start();
    }
}

class Number8 {

    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }
}
