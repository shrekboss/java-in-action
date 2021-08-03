
package org.crayzer.spring.aop.features;

import org.crayzer.spring.aop.features.interceptor.EchoServiceMethodInterceptor;
import org.crayzer.spring.aop.overview.DefaultEchoService;
import org.crayzer.spring.aop.overview.EchoService;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author crayzer
 */
public class ProxyFactoryDemo {

    public static void main(String[] args) {
        DefaultEchoService defaultEchoService = new DefaultEchoService();
        // 注入目标对象（被代理）
        ProxyFactory proxyFactory = new ProxyFactory(defaultEchoService);
        proxyFactory.setTargetClass(DefaultEchoService.class);
        // 添加 Advice 实现 MethodInterceptor < Interceptor < Advice
        proxyFactory.addAdvice(new EchoServiceMethodInterceptor());
        // 获取代理对象
        EchoService echoService = (EchoService) proxyFactory.getProxy();
        System.out.println(echoService.echo("Hello,World"));
    }
}
