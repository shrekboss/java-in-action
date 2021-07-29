package org.crayzer.design.design_mode_pattern.structural.proxy.staticProxy;

import org.crayzer.design.design_mode_pattern.structural.proxy.UserDaoImpl;

public class StaticProxyApp {
    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();
        UserDaoImplProxy proxy = new UserDaoImplProxy(userDao);

        proxy.save();
    }
}
