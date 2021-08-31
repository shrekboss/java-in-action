package org.crayzer.err.coding.httpinvoke.feignandribbontimeout;

import org.crayzer.err.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplicationDefault {

    public static void main(String[] args) {
        Utils.loadPropertySource(
                FeignAndRibbonController.class,
                "default.properties");
        SpringApplication.run(CommonMistakesApplicationDefault.class, args);
    }
}

