package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample03;

import org.crayzer.design.design_mode_pattern.behavioural.observer.sample.core.EventListener;

public class Mouse extends EventListener {
    public void click() {
        System.out.println("鼠标单击");
        this.trigger(MouseEventType.ON_CLICK);
    }

    public void doubleClick() {
        System.out.println("鼠标双击");
        this.trigger(MouseEventType.ON_DOUBLE_CLICK);
    }

    public void up() {
        System.out.println("鼠标弹起");
        this.trigger(MouseEventType.ON_UP);
    }

    public void down() {
        System.out.println("鼠标按下");
        this.trigger(MouseEventType.ON_DOWN);
    }


    public void wheel() {
        System.out.println("鼠标滚动");
        this.trigger(MouseEventType.ON_WHEEL);
    }

    public void move() {
        System.out.println("鼠标移动");
        this.trigger(MouseEventType.ON_MOVE);
    }

    public void over() {
        System.out.println("鼠标悬停");
        this.trigger(MouseEventType.ON_OVER);
    }
}
