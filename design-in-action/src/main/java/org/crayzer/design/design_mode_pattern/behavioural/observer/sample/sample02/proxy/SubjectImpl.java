package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample02.proxy;

public class SubjectImpl implements ISubject {
    @Override
    public void add() {
        System.out.println("调用添加的方法");
    }

    @Override
    public void remove() {
        System.out.println("调用删除的方法");
    }
}
