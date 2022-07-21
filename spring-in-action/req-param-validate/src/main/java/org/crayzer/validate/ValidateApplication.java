package org.crayzer.validate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * class_name: Application
 * package: org.crayzer.springmvc.best.practice
 * describe: 用于展示Spring应用的最佳实践
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 **/
@EnableAsync
@SpringBootApplication
public class ValidateApplication {
    public static void main(String[] args) {
        SpringApplication.run(ValidateApplication.class, args);
    }
}
