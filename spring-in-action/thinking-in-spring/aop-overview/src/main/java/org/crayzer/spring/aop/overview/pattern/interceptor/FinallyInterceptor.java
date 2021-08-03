package org.crayzer.spring.aop.overview.pattern.interceptor;

import java.lang.reflect.Method;

/**
 * 最终执行后置拦截器
 *
 * @author crayzer
 */
public interface FinallyInterceptor {

    Object finalize(Object proxy, Method method, Object[] args, Object returnResult);
}
