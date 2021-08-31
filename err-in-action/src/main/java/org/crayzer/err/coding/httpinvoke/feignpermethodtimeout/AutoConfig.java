package org.crayzer.err.coding.httpinvoke.feignpermethodtimeout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.crayzer.err.coding.httpinvoke.feignpermethodtimeout")
public class AutoConfig {
}
