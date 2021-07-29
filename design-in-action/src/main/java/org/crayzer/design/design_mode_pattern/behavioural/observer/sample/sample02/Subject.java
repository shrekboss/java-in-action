package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample02;


import org.crayzer.design.design_mode_pattern.behavioural.observer.sample.core.EventListener;

public class Subject extends EventListener {
    public void add () {
        System.out.println("调用添加的方法");
        trigger(SubjectEventType.ON_ADD);
    }

    public void remove () {
        System.out.println("调用删除的方法");
        trigger(SubjectEventType.ON_REMOVE);
    }
}
