package org.crayzer.err.coding.transaction.transactionproxyfailed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, ".");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

