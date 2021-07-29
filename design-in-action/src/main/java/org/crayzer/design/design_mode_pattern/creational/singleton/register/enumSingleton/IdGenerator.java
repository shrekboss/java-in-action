package org.crayzer.design.design_mode_pattern.creational.singleton.register.enumSingleton;

import java.util.concurrent.atomic.AtomicLong;

public enum IdGenerator {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }
}
