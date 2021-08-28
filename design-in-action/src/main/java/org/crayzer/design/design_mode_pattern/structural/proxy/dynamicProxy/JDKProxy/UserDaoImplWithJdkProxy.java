package org.crayzer.design.design_mode_pattern.structural.proxy.dynamicProxy.JDKProxy;


import org.crayzer.design.design_mode_pattern.structural.proxy.IUserDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class UserDaoImplWithJdkProxy implements InvocationHandler {
    private IUserDao target;

    public UserDaoImplWithJdkProxy(IUserDao target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class[]{IUserDao.class},
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        startTransaction();

        Object returnValue = method.invoke(target, args);

        commitTransaction();

        return returnValue;
    }

    private void startTransaction() {
        System.out.println("开始事务...");
    }

    private void commitTransaction() {
        System.out.println("提交事务...");
    }
}
