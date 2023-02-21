package org.crayzer.err.coding.transaction.transactionproxyfailed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableTransactionManagement
public class CommonMistakesApplication {

    public static void main(String[] args) {
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, ".");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

