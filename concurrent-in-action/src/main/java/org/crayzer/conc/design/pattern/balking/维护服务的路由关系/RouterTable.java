package org.crayzer.conc.design.pattern.balking.维护服务的路由关系;

import java.util.Set;
import java.util.concurrent.*;

/**
 * describe: 采用 volatile 来实现，是因为对共享变量 changed 和 rt 的写操作不存在原子性的要求
 **/
public class RouterTable {
    //Key:接口名  Value:路由集合
    private ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> rt
            = new ConcurrentHashMap<>();

    //路由表是否发生变化
    volatile boolean changed;
    /**
     * 将路由表写入本地文件的线程池
     * scheduleWithFixedDelay() 这种调度方式能保证同一时刻只有一个线程执行 autoSave() 方法
     */
    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    public void startLocalSaver() {
        ses.scheduleWithFixedDelay(() -> {
            autoSave();
        }, 1, 1, TimeUnit.MINUTES);
    }

    private void autoSave() {
        if (!changed) return;
        changed = false;
        this.save2Local();
    }

    public Set<Router> get(String iface) {
        return rt.get(iface);
    }

    public void remove(Router router) {
        Set<Router> set = rt.get(router.getIface());
        if (set != null) {
            set.remove(router);
            changed = true;
        }
    }

    //增加路由
    public void add(Router router) {
        Set set = rt.computeIfAbsent(router.getIface(), r -> new CopyOnWriteArraySet<>());
        set.add(router);
        //路由表已发生变化
        changed = true;
    }

    private void save2Local() {
    }
}


















