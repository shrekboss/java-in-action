package org.crayzer.design.design_mode_pattern.structural.proxy.customJDKProxy;

import org.crayzer.design.design_mode_pattern.structural.proxy.IUserDao;

import java.lang.reflect.Method;

public class UserDaoImplWithCrayzerProxy implements CrayzerInvocationHandler {
    private IUserDao target;

    public UserDaoImplWithCrayzerProxy(IUserDao target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return MyProxy.newProxyInstance(
                new MyClassLoader(),
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
