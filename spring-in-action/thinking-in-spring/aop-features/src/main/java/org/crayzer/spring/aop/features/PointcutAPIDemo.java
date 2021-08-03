
package org.crayzer.spring.aop.features;

import org.crayzer.spring.aop.features.interceptor.EchoServiceMethodInterceptor;
import org.crayzer.spring.aop.features.pointcut.EchoServiceEchoMethodPointcut;
import org.crayzer.spring.aop.features.pointcut.EchoServicePointcut;
import org.crayzer.spring.aop.overview.DefaultEchoService;
import org.crayzer.spring.aop.overview.EchoService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author crayzer
 */
public class PointcutAPIDemo {

    public static void main(String[] args) {

        EchoServicePointcut echoServicePointcut = new EchoServicePointcut("echo", EchoService.class);

        ComposablePointcut pointcut = new ComposablePointcut(EchoServiceEchoMethodPointcut.INSTANCE);
        // 组合实现
        pointcut.intersection(echoServicePointcut.getClassFilter());
        pointcut.intersection(echoServicePointcut.getMethodMatcher());


        // 将 Pointcut 适配成 Advisor

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new EchoServiceMethodInterceptor());

        DefaultEchoService defaultEchoService = new DefaultEchoService();
        ProxyFactory proxyFactory = new ProxyFactory(defaultEchoService);
        // 添加 Advisor
        proxyFactory.addAdvisor(advisor);

        // 获取代理对象
        EchoService echoService = (EchoService) proxyFactory.getProxy();
        System.out.println(echoService.echo("Hello,World"));
    }
}
