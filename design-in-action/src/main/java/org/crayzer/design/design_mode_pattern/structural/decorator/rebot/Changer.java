package org.crayzer.design.design_mode_pattern.structural.decorator.rebot;

public abstract class Changer implements Transform {
    private Transform transform;

    public Changer(Transform transform) {
        this.transform = transform;
    }

    @Override
    public void move() {
        transform.move();
    }

}
