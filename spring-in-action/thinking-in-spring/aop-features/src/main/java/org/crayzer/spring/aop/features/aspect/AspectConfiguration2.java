
package org.crayzer.spring.aop.features.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

/**
 * Aspect 配置类
 *
 * @author crayzer
 */
@Aspect
public class AspectConfiguration2 implements Ordered {

    @Before("execution(public * *(..))")          // Join Point 拦截动作
    public void beforeAnyPublicMethod2() {
        System.out.println("@Before any public method.(2)");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
