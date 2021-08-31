package org.crayzer.err.coding.httpinvoke.ribbonretry;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "org.crayzer.err.coding.httpinvoke.ribbonretry.feign")
public class AutoConfig {
}
