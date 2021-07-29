package org.crayzer.design.design_mode_pattern.structural.proxy.dynamicProxy.CglibProxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.crayzer.design.design_mode_pattern.structural.proxy.UserDaoImpl;

import java.lang.reflect.Method;

public class UserDaoImplWithCglibProxy implements MethodInterceptor {
    private UserDaoImpl target;

    public UserDaoImplWithCglibProxy(UserDaoImpl target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        // 1.获取工具类
        Enhancer enhancer = new Enhancer();
        // 2.设置父类
        enhancer.setSuperclass(target.getClass());
        // 3.设置回调函数
        enhancer.setCallback(this);
        // 4.创建子类(代理对象)
        return enhancer.create();
    }

    private void startTransaction() {
        System.out.println("开始事务...");
    }

    private void commitTransaction() {
        System.out.println("提交事务...");
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        startTransaction();
        Object returnValue = method.invoke(target, args);
        commitTransaction();
        return returnValue;
    }
}
