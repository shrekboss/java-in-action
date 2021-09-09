package org.crayzer.err.coding.springpart2.propertysource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.StandardEnvironment;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class CommonMistakesApplication {
    @Autowired
    private StandardEnvironment env;

    public static void main(String[] args) {
        //环境变量添加 MANAGEMENT_SERVER_PORT=12345 MANAGEMENT_SERVER_IP=192.168.0.2
        System.setProperty("management.server.port", "12345");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @PostConstruct
    public void init() {
        Arrays.asList("user.name", "management.server.port").forEach(key -> {
            env.getPropertySources().forEach(propertySource -> {
                if (propertySource.containsProperty(key)) {
                    log.info("{} -> {} 实际取值：{}", propertySource, propertySource.getProperty(key), env.getProperty(key));
                }
            });
        });

        System.out.println("配置优先级：");
        env.getPropertySources().stream().forEach(System.out::println);
    }
}

