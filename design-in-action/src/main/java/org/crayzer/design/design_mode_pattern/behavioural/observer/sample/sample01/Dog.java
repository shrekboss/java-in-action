package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample01;

public class Dog implements MyObserver {
    @Override
    public void response() {
        System.out.println("狗跟着叫！");
    }
}
