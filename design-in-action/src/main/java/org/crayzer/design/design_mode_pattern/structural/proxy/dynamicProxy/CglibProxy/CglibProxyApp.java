package org.crayzer.design.design_mode_pattern.structural.proxy.dynamicProxy.CglibProxy;


import org.crayzer.design.design_mode_pattern.structural.proxy.IUserDao;
import org.crayzer.design.design_mode_pattern.structural.proxy.UserDaoImpl;

public class CglibProxyApp {
    public static void main(String[] args) {
        UserDaoImpl target = new UserDaoImpl();
        IUserDao proxy = (IUserDao) new UserDaoImplWithCglibProxy(target).getProxyInstance();
        System.out.println(proxy);

        proxy.save();
        proxy.edit();
        proxy.delete();
    }
}
