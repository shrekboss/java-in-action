package org.crayzer.conc.juc;

import java.util.concurrent.TimeUnit;

public class SleepTools {

    public static void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }

    public static void ms(long millisecond) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecond);
        } catch (InterruptedException e) {
        }
    }
}
