package org.crayzer.err.coding.lock.lockscope;


public class WrongData {
    private static int counter = 0;

    public static int reset() {
        counter = 0;
        return counter;
    }

    // 锁的 scope 错误
    public synchronized void wrong() {
        counter++;
    }

    public static int getCounter() {
        return counter;
    }
}
