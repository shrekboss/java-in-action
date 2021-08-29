package org.crayzer.err.coding.lock.lockscope;


public class RightData {
    private static int counter = 0;
    // 注意点 static 和 非 static 也会导致结果和预期不一致
    private final static Object locker = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    public void right() {
        // this 和 locker 的区别 结果：使用this，locker
        synchronized (locker) {
            counter++;
        }
    }

    public static int getCounter() {
        return counter;
    }
}
