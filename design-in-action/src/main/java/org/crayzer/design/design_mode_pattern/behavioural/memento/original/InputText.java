package org.crayzer.design.design_mode_pattern.behavioural.memento.original;

public class InputText {
    private StringBuilder text = new StringBuilder();

    public String getText() {
        return text.toString();
    }

    public void append(String input) {
        text.append(input);
    }

    // 1. 函数有可能会被其他业务使用，所以，暴露不应该暴露的函数违背了封装原则
    public void setText(String text) {
        this.text.replace(0, this.text.length(), text);
    }
}

