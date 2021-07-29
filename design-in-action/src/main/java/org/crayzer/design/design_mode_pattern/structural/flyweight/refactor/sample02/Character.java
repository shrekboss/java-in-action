package org.crayzer.design.design_mode_pattern.structural.flyweight.refactor.sample02;

public class Character {
    private char c;
    private CharacterStyle style;

    public Character(char c, CharacterStyle style) {
        this.c = c;
        this.style = style;
    }
}
