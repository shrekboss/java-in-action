package org.crayzer.design.design_mode_refactor.test;

public class RedisDistributedLock {
    private static final RedisDistributedLock instance = new RedisDistributedLock();

    private RedisDistributedLock() {}

    public static RedisDistributedLock getSingletonInstance() {
        return instance;
    }

    public boolean lockTransaction(String id) {
        return false;
    }

    public void unlockTransaction(String id) {

    }
}
