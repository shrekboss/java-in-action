package org.crayzer.design.design_mode_principle.dip.ioc.after;

import java.util.ArrayList;
import java.util.List;

public class JunitApplication {

    private static List<TestCase> testCases = new ArrayList<>();

    public static void register(TestCase testCase) {
        testCases.add(testCase);
    }

    public static void main(String[] args) {
        // 注册操作还可以通过配置的方式来实现，不需要程序员显示调用register()
        JunitApplication.register(new UserServiceTest());
        for (TestCase c : testCases) {
            c.run();
        }
    }
}
