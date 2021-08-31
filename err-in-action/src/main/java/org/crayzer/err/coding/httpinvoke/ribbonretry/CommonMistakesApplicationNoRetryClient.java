package org.crayzer.err.coding.httpinvoke.ribbonretry;

import org.crayzer.err.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplicationNoRetryClient {

    public static void main(String[] args) {
        System.setProperty("server.port", "45679");

        Utils.loadPropertySource(CommonMistakesApplicationNoRetryClient.class,"noretry-ribbon.properties");
        SpringApplication.run(CommonMistakesApplicationNoRetryClient.class, args);
    }
}

