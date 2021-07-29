package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample01;

import java.util.ArrayList;
import java.util.List;

public abstract class MySubject {
    protected List<MyObserver> observers = new ArrayList<>();

    // 注册
    public void attach(MyObserver observer) {
        observers.add(observer);
    }

    // 撤销
    public void detach(MyObserver observer) {
        observers.remove(observer);
    }

    /**
     * 抽象通知方法
     */
    public abstract void cry();

}
