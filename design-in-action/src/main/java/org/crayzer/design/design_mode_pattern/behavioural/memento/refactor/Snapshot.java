package org.crayzer.design.design_mode_pattern.behavioural.memento.refactor;

// 不可变对象
public class Snapshot {
    private String text;

    public Snapshot(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
