package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample02.proxy;


import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.crayzer.design.design_mode_pattern.behavioural.observer.sample.core.EventListener;
import org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample02.SubjectEventType;

import java.lang.reflect.Method;

public class SubjectProxy extends EventListener implements InvocationHandler {
    private Object target;

    private Enum type;
    private Object observer;

    public SubjectProxy(Object target, Object observer, SubjectEventType type) {
        this.target = target;
        this.observer = observer;
        this.type = type;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object retVal = method.invoke(target, args);
// System.out.println(method.getName());
        this.addListener(type, observer);
        this.trigger(type);
        return retVal;
    }
}
