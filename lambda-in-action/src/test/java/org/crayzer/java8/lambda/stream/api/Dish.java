package org.crayzer.java8.lambda.stream.api;

import lombok.Data;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
@Data
public class Dish {

    private String name;
    private boolean vegetarian;
    private int calories;

    private Type type;
}
