package org.crayzer.design.design_mode_pattern.structural.adapter.object_adapter;

import org.crayzer.design.design_mode_pattern.structural.adapter.Source;

public class AdapterTest {
    public static void main(String[] args) {
        Source source = new Source();
        Adapter adapter = new Adapter(source);

        adapter.method1();
        adapter.method2();
    }
}
