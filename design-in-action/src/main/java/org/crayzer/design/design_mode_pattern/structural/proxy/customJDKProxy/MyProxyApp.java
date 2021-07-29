package org.crayzer.design.design_mode_pattern.structural.proxy.customJDKProxy;

import org.crayzer.design.design_mode_pattern.structural.proxy.IUserDao;
import org.crayzer.design.design_mode_pattern.structural.proxy.UserDaoImpl;

import java.io.IOException;

public class MyProxyApp {
    public static void main(String[] args) throws IOException {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        IUserDao proxy = (IUserDao) new UserDaoImplWithCrayzerProxy(userDaoImpl).getProxyInstance();
        System.out.println(proxy);

        proxy.save();

        proxy.edit();

    }
}
