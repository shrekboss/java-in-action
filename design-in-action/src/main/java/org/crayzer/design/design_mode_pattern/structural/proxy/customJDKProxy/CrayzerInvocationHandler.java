package org.crayzer.design.design_mode_pattern.structural.proxy.customJDKProxy;

import java.lang.reflect.Method;

public interface CrayzerInvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
