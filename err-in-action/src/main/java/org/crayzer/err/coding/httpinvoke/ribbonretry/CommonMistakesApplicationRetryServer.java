package org.crayzer.err.coding.httpinvoke.ribbonretry;

import org.crayzer.err.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplicationRetryServer {

    public static void main(String[] args) {
        System.setProperty("server.port", "45678");
        System.setProperty("management.server.port", "12345");
        Utils.loadPropertySource(
                CommonMistakesApplicationRetryServer.class,
                "default-ribbon.properties");
        SpringApplication.run(CommonMistakesApplicationRetryServer.class, args);
    }
}

