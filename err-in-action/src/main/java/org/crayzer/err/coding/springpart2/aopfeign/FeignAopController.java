package org.crayzer.err.coding.springpart2.aopfeign;

import lombok.extern.slf4j.Slf4j;
import org.crayzer.err.coding.springpart2.aopfeign.feign.Client;
import org.crayzer.err.coding.springpart2.aopfeign.feign.ClientWithUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("feignaop")
@RestController
public class FeignAopController {

    @Autowired
    private Client client;

    @Autowired
    private ClientWithUrl clientWithUrl;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * curl http://localhost:45678/feignaop/client
     */
    @GetMapping("client")
    public String client() {
        return client.api();
    }

    /**
     * curl http://localhost:45678/feignaop/clientWithUrl
     * <p/>
     * 设置 jdk代理：spring.aop.proxy-target-class=false
     * <p/>
     * pom 取消对 spring-cloud-starter-netflix-ribbon 依赖
     */
    @GetMapping("clientWithUrl")
    public String clientWithUrl() {
        return clientWithUrl.api();
    }

    /**
     * curl http://localhost:45678/feignaop/server
     */
    @GetMapping("server")
    public String server() {
        return "OK";
    }
}
