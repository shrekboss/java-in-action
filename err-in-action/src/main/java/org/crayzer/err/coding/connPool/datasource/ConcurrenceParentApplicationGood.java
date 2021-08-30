package org.crayzer.err.coding.connPool.datasource;

import org.crayzer.err.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcurrenceParentApplicationGood {

    public static void main(String[] args) {
    	/* connPool#datasource */
		Utils.loadPropertySource(ConcurrenceParentApplicationGood.class, "good.properties");
    	SpringApplication.run(ConcurrenceParentApplicationGood.class, args);
    }

}
