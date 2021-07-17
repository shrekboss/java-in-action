package org.crayzer.conc.jdkconcurrentutil.lock.readwritelock;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Cache<K,V> {
    private final Map<K, V> m = new HashMap<>();
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();

    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    // 读缓存
    V get(K key) {
        r.lock();
        try { return m.get(key); }
        finally { r.unlock(); }
    }
    // 写缓存
    V put(K key, V value) {
        w.lock();
        try { return m.put(key, value); }
        finally { w.unlock(); }
    }
    // 清空所有的内容
    void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }
}
