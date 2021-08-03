package org.crayzer.spring.aop.overview.pattern.proxy;

import org.crayzer.spring.aop.overview.pattern.DefaultEchoService;
import org.crayzer.spring.aop.overview.pattern.EchoService;
import org.crayzer.spring.aop.overview.pattern.ProxyEchoService;

/**
 * 静态代理示例
 *
 * @author crayzer
 */
public class StaticProxyDemo {

    public static void main(String[] args) {
        EchoService echoService = new ProxyEchoService(new DefaultEchoService());
        echoService.echo("Hello,World");
    }
}
