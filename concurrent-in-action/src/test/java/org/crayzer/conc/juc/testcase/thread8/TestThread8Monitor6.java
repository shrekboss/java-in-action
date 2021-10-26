package org.crayzer.conc.juc.testcase.thread8;

import org.junit.Test;

/**
 * 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor6 {

    @Test
    public void thread8MonitorTest() {
        Number6 number = new Number6();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }//这样其实不能通过类的实例访问静态，为演示这个问题
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getTwo();
            }
        }).start();
    }
}

class Number6 {
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
