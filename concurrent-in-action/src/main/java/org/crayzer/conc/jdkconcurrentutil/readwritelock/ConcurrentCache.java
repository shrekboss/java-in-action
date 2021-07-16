package org.crayzer.conc.jdkconcurrentutil.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 高并发的场景下，有可能会有多线程竞争写锁
 * {@see ⑥}
 */
public class ConcurrentCache<K, V> {
    private final Map<K, V> m = new HashMap<>();
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    // 读锁
    private final Lock r = rwl.readLock();
    // 写锁
    private final Lock w = rwl.writeLock();

    // 读缓存

    V get(K key) {
        V v = null;
        //读缓存 ①
        r.lock();
        try {
            //todo: 不可以 增加验证缓存并更新缓存(读写锁不支持锁升级) ②
            v = m.get(key);
        } finally {
            // ③
            r.unlock();
        }
        //缓存中存在，返回  ④
        if (v != null) {
            return v;
        }
        //缓存中不存在，查询数据库 ⑤
        w.lock();
        try {
            //再次验证
            //其他线程可能已经查询过数据库 ⑥
            v = m.get(key);
            // ⑦
            if (v == null) {
                //查询数据库
                // v = 省略代码无数
                m.put(key, v);
            }
        } finally {
            w.unlock();
        }
        return v;
    }

    // 写缓存
    V put(K key, V value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }
}
