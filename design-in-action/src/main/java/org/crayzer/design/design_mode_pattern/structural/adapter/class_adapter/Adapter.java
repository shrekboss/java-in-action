package org.crayzer.design.design_mode_pattern.structural.adapter.class_adapter;

import org.crayzer.design.design_mode_pattern.structural.adapter.Source;
import org.crayzer.design.design_mode_pattern.structural.adapter.Targetable;

public class Adapter extends Source implements Targetable {

    @Override
    public void method2() {
        System.out.println("this is the targetable method!");
    }
}
