package org.crayzer.conc.design.pattern.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {
    Map<Thread, T> locals = new HashMap<>();

    T get() {
        return locals.get(Thread.currentThread());
    }

    void set(T t) {
        locals.put(Thread.currentThread(), t);
    }
}


