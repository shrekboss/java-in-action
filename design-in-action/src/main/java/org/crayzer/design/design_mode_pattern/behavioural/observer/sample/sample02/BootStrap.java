package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample02;


import org.crayzer.design.design_mode_pattern.behavioural.observer.sample.core.Event;

import java.lang.reflect.Method;

public class BootStrap {
    public static void main(String[] args) {
        try {
            Observer observer = new Observer();
            Method advice = Observer.class.getMethod("advice", Event.class);

            Subject subject = new Subject();
            subject.addListener(SubjectEventType.ON_ADD, observer, advice);
            subject.addListener(SubjectEventType.ON_REMOVE, observer, advice);
            subject.addListener(SubjectEventType.ON_EDIT, observer, advice);
            subject.addListener(SubjectEventType.ON_QUERY, observer, advice);

            subject.add();
            subject.remove();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
