package org.crayzer.spring.aop.overview.pattern.interceptor;

import java.lang.reflect.Method;

/**
 * @author crayzer
 */
public interface ExceptionInterceptor {

    void intercept(Object proxy, Method method, Object[] args, Throwable throwable);
}
