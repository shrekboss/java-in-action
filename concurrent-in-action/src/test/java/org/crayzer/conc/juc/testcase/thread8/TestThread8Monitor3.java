package org.crayzer.conc.juc.testcase.thread8;

import org.junit.Test;

/**
 * 新增普通方法 getThree() , 打印? //three  one   two
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor3 {

    @Test
    public void thread8MonitorTest() {
        Number3 number = new Number3();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getThree();
            }
        }).start();
    }
}

class Number3 {
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

    //普通方法
    public void getThree() {
        System.out.println("three");
    }
}
