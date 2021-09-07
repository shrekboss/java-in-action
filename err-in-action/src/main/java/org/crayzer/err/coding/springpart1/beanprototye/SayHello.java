package org.crayzer.err.coding.springpart1.beanprototye;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Slf4j
// 让 Service 以代理方式注入。这样虽然 Controller 本身是单例的，但每次都能从代理获取 Service。
// 这样一来，prototype 范围的配置才能真正生效
// @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)

// 单例的 Controller 注入的 Service 也是一次性创建的，即使 Service 本身标识了 prototype 的范围也没用
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SayHello extends SayService {

    @Override
    public void say() {
        super.say();
        log.info("hello");
    }
}
