package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample01;

public class Pig implements MyObserver {
    @Override
    public void response() {
        System.out.println("猪没有反应！");
    }
}
