package org.crayzer.spring.aop.overview.pattern.interceptor;

import java.lang.reflect.Method;

/**
 * 前置拦截器
 *
 * @author crayzer
 */
public interface BeforeInterceptor {

    /**
     * 前置执行
     */
    Object before(Object proxy, Method method, Object[] args);
}
