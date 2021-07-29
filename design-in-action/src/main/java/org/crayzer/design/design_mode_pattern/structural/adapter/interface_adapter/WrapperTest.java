package org.crayzer.design.design_mode_pattern.structural.adapter.interface_adapter;

import org.crayzer.design.design_mode_pattern.structural.adapter.Targetable;

public class WrapperTest {
    public static void main(String[] args) {
        Targetable sub1 = new SourceSub1();
        sub1.method1();

        Targetable sub2 = new SourceSub2();
        sub2.method2();
    }
}
