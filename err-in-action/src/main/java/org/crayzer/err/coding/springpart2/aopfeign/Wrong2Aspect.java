package org.crayzer.err.coding.springpart2.aopfeign;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
// @Component
public class Wrong2Aspect {

    // 切入的是 ClientWithUrl 接口的 API 方法，并不是 client.Feign 接口的 execute 方法，显然不符合预期。
    // @FeignClient 注解标记在 Feign Client 接口上，所以切的是 Feign 定义的接口，也就是每一个实际的
    // API 接口
    @Before("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void before(JoinPoint pjp) {
        log.info("@within(org.springframework.cloud.openfeign.FeignClient) pjp {}, args:{}", pjp, pjp.getArgs());
    }
}
