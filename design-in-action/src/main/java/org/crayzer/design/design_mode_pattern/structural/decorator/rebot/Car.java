package org.crayzer.design.design_mode_pattern.structural.decorator.rebot;

public class Car implements Transform {
    public Car() {
        System.out.println("变形金刚是一辆车！");
    }

    @Override
    public void move() {
        System.out.println("在陆地上移动！");
    }

}
