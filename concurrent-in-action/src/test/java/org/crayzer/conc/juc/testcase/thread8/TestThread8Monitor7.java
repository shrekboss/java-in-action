package org.crayzer.conc.juc.testcase.thread8;

import org.junit.Test;

/**
 * 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor7 {

    @Test
    public void thread8MonitorTest() {
        Number7 number = new Number7();
        Number7 number2 = new Number7();
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

class Number7 {
    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("one");
    }
    public  synchronized void getTwo() {
        System.out.println("two");
    }
}
