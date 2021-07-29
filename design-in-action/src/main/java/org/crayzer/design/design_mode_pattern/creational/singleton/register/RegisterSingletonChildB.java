package org.crayzer.design.design_mode_pattern.creational.singleton.register;

public class RegisterSingletonChildB extends RegisterSingleton {
    public static RegisterSingletonChildB getInstance() {
        return (RegisterSingletonChildB) RegisterSingletonChildB
                .getInstance("org.crayzer.design.design_mode_pattern.creational.singleton.register.RegisterSingletonChildB");
    }

    //随便写一个测试的方法
    public String about() {
        return "---->我是RegisterSingleton的一个子类RegisterSingletonChildB";
    }
}
