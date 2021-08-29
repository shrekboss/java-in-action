package org.crayzer.err.coding.concurrentTools.threadlocal;

import org.crayzer.err.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class, "tomcat.properties");

        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

