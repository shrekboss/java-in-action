package org.crayzer.design.design_mode_thingking.abstraction.mock.mockImpl;

import org.crayzer.design.design_mode_thingking.abstraction.mock.MockInterface;

public class MockInterfaceImpl extends MockInterface {
    @Override
    public void method() {
        System.out.println(123);
    }

    public static void main(String[] args) {
        // MockInterface mockInterface = new MockInterface();
        MockInterface mockInterface = new MockInterfaceImpl();
        System.out.println(mockInterface);
        mockInterface.method();
    }
}
