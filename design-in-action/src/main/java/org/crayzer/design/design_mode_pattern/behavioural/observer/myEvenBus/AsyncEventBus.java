package org.crayzer.design.design_mode_pattern.behavioural.observer.myEvenBus;

import java.util.concurrent.Executor;

/**
 * 实现异步非阻塞的观察者模式
 */
public class AsyncEventBus extends EventBus {
    public AsyncEventBus(Executor executor) {
        super(executor);
    }
}
