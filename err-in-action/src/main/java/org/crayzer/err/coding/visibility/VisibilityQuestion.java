package org.crayzer.err.coding.visibility;

import java.util.concurrent.TimeUnit;

public class VisibilityQuestion {

    // 可见性问题
    // private static boolean b = true;
    private static volatile boolean b = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) { }
            b =false;
        }).start();
        while (b) {
            TimeUnit.MILLISECONDS.sleep(0);
        }
        System.out.println("done");
    }
}
