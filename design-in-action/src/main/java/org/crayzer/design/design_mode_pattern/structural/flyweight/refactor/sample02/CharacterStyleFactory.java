package org.crayzer.design.design_mode_pattern.structural.flyweight.refactor.sample02;

import org.crayzer.design.design_mode_pattern.structural.flyweight.original.sample02.Font;

import java.util.ArrayList;
import java.util.List;

public class CharacterStyleFactory {
    private static final List<CharacterStyle> styles = new ArrayList<>();

    public static CharacterStyle getStyle(Font font, int size, int colorRGB) {
        CharacterStyle newStyle = new CharacterStyle(font, size, colorRGB);
        for (CharacterStyle style : styles) {
            if (style.equals(newStyle)) {
                return style;
            }
        }
        styles.add(newStyle);
        return newStyle;
    }
}
