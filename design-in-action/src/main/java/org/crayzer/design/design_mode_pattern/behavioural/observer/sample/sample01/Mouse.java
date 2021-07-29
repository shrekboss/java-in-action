package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample01;

public class Mouse implements MyObserver{
    @Override
    public void response() {
        System.out.println("老鼠努力奔跑...");
    }
}
