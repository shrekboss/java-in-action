package org.crayzer.design.design_mode_pattern.structural.flyweight.refactor.sample01;


import lombok.Getter;

// 享元类 不可变对象 不能有 setter 方法
@Getter
public class ChessPieceUnit {
    private int id;
    private String text;
    private Color color;

    public ChessPieceUnit(int id, String text, Color color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    public static enum Color {
        RED, BLACK
    }

    // ...省略其他属性和getter方法...
}

