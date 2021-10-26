package org.crayzer.conc.juc.testcase.thread8;

import org.junit.Test;

/**
 * 新增 Thread.sleep() 给 getOne() ,打印? //one  two
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThread8Monitor2 {
    @Test
    public void thread8MonitorTest() {
        Number2 number = new Number2();
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

class Number2 {
    public synchronized void getOne() {
        try {
            Thread.sleep(3000);//让one 睡3秒
        } catch (InterruptedException e) {
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }
}
