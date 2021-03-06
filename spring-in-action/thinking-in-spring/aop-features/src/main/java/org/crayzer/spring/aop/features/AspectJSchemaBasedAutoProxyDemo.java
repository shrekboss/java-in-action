
package org.crayzer.spring.aop.features;

import org.crayzer.spring.aop.overview.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于 XML 配置自动代理示例
 *
 * @author crayzer
 */
public class AspectJSchemaBasedAutoProxyDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-auto-proxy-context.xml");

        EchoService echoService = context.getBean("echoService", EchoService.class);

        echoService.echo("Hello,World");
//        System.out.println(echoService.echo("Hello,World"));

        context.close();
    }
}
