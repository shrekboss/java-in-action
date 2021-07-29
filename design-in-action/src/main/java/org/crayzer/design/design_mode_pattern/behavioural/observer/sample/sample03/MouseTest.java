package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample03;

import org.crayzer.design.design_mode_pattern.behavioural.observer.sample.core.Event;

import java.lang.reflect.Method;

public class MouseTest {
    public static void main(String[] args) {
        //观察者和被观察者之间没有必然联系
        //注册的时候，才产生联系
        //解耦
        try {
            MouseEventCallback callback = new MouseEventCallback();
            Method onClick = MouseEventCallback.class.getMethod("onClick", Event.class);


            //人为的调用鼠标的单击事件
            Mouse mouse = new Mouse();
            mouse.addListener(MouseEventType.ON_CLICK, callback, onClick);

            mouse.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
