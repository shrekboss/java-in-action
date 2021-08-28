package org.crayzer.design.design_mode_pattern.creational.builder.sample01;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public interface Matter {

    String scene();

    String brand();

    String model();

    BigDecimal price();

    String desc();
}
