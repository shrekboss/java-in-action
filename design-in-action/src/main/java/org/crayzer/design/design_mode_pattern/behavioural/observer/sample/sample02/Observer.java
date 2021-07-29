package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample02;

import org.crayzer.design.design_mode_pattern.behavioural.observer.sample.core.Event;

public class Observer {
    public void advice (Event e) {
        System.out.println("=========触发事件，打印日志========\n" + e);
    }
}
