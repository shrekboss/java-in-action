package org.crayzer.design.design_mode_pattern.creational.singleton.register;

public class RegisterSingletonChildA extends RegisterSingleton {
    public static RegisterSingletonChildA getInstance() {
        return (RegisterSingletonChildA) RegisterSingletonChildA
                .getInstance("org.crayzer.design.design_mode_pattern.creational.singleton.register.RegisterSingletonChildA");
    }

    //随便写一个测试的方法
    public String about() {
        return "---->我是RegisterSingleton的一个子类RegisterSingletonChildA";
    }
}
