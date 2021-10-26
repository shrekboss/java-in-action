package org.crayzer.conc.juc.thread8;

import org.junit.Test;

/**
 * 修改 getOne() 为静态同步方法，打印?  //two   one
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor5 {

    @Test
    public void thread8MonitorTest(String[] args) {
        Number5 number = new Number5();
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
class Number5 {
    //静态同步方法
    public static synchronized void getOne() {
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
