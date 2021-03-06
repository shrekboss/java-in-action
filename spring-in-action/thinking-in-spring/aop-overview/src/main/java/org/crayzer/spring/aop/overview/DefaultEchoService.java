package org.crayzer.spring.aop.overview;

import org.springframework.context.annotation.Configuration;

/**
 * 默认 {@link EchoService} 实现
 *
 * @author crayzer
 */
@Configuration // @Configuration 需要 @ComponentScan -> ConfigurationClassPostProcessor
// CGLIB 代理对象
public class DefaultEchoService implements EchoService {

    @Override
    public String echo(String message) {
        System.out.println("[ECHO] " + message);
        return "[ECHO] " + message;
    }
}
