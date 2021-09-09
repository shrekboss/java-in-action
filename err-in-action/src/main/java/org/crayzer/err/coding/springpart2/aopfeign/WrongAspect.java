package org.crayzer.err.coding.springpart2.aopfeign;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
//AOP切入feign.Client的实现
public class WrongAspect {
    // 通过 feign.Client 接口切的是客户端实现类，切到的是通用的、执行所有 Feign 调用的 execute 方法。
    @Before("within(feign.Client+)")
    public void before(JoinPoint pjp) {
        log.info("within(feign.Client+) pjp {}, args:{}", pjp, pjp.getArgs());
    }
}

// 为什么 within(feign.Client+) 无法切入设置过 URL 的 @FeignClient ClientWithUrl：
// 1. 表达式声明的是切入 feign.Client 的实现类。
// 2. Spring 只能切入由自己管理的 Bean。
// 3. 虽然 LoadBalancerFeignClient 和 ApacheHttpClient 都是 feign.Client 接口的实现，但是
// HttpClientFeignLoadBalancedConfiguration 的自动配置只是把前者定义为 Bean，后者是 new 出来的、
// 作为了 LoadBalancerFeignClient 的 delegate，不是 Bean。
// 4. 在定义了 FeignClient 的 URL 属性后，获取的是 LoadBalancerFeignClient 的 delegate，它不是 Bean。
//
// 因此，定义了 URL 的 FeignClient 采用 within(feign.Client+) 无法切入。
