package org.crayzer.err.coding.httpinvoke.ribbonretry;

import org.crayzer.err.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplicationRetryClient {

    public static void main(String[] args) {
        System.setProperty("server.port", "45679");
        Utils.loadPropertySource(
                CommonMistakesApplicationRetryClient.class,
                "default-ribbon.properties");
        SpringApplication.run(CommonMistakesApplicationRetryClient.class, args);
    }
}

