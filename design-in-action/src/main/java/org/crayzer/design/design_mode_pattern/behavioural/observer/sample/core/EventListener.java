package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventListener {
    private Map<Enum, Event> events = new HashMap<>();

    public void addListener (Enum eventType, Object target, Method callback) {
        events.put(eventType, new Event(target, callback));
    }

    public void addListener (Enum eventType, Object target) {
        events.put(eventType, new Event(target));
    }

    private void trigger (Event event) {
        event.setSource(this);
        event.setTime(System.currentTimeMillis());

        try {
            Method[] methods = event.getTarget().getClass().getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                method.invoke(event.getTarget(), event);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void trigger(Enum eventType) {
        if (!events.containsKey(eventType)) {
            return;
        }

        trigger(events.get(eventType).setTrigger(eventType.toString()));
    }
}
