package org.crayzer.err.coding.springpart1.beansingletoncirculardependency;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestD {
    @Getter
    private TestC testC;

    @Autowired
    public TestD(TestC testC) {
        this.testC = testC;
    }
}
