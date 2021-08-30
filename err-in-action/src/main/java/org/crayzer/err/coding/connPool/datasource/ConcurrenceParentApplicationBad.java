package org.crayzer.err.coding.connPool.datasource;

import org.crayzer.err.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcurrenceParentApplicationBad {

    public static void main(String[] args) {
    	/* connPool#datasource */
		Utils.loadPropertySource(ConcurrenceParentApplicationBad.class, "good.properties");
    	SpringApplication.run(ConcurrenceParentApplicationBad.class, args);
    }

}
