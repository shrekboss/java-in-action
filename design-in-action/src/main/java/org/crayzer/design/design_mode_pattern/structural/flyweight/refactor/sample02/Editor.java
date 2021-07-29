package org.crayzer.design.design_mode_pattern.structural.flyweight.refactor.sample02;

import org.crayzer.design.design_mode_pattern.structural.flyweight.original.sample02.Font;

import java.util.ArrayList;
import java.util.List;

public class Editor {
    private List<Character> chars = new ArrayList<>();

    public void appendCharacter(char c, Font font, int size, int colorRGB) {
        Character character = new Character(c, CharacterStyleFactory.getStyle(font, size, colorRGB));
        chars.add(character);
    }
}
