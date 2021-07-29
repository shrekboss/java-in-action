package org.crayzer.design.design_mode_pattern.structural.adapter.object_adapter;

import org.crayzer.design.design_mode_pattern.structural.adapter.Source;
import org.crayzer.design.design_mode_pattern.structural.adapter.Targetable;

public class Adapter implements Targetable {

    private Source source;

    public Adapter(Source source) {
        this.source = source;
    }

    @Override
    public void method1() {
        source.method1();
    }

    @Override
    public void method2() {
        System.out.println("this is the targetable method!");
    }
}
