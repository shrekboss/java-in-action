package org.crayzer.design.design_mode_pattern.creational.builder.sample01.ceilling;

import org.crayzer.design.design_mode_pattern.creational.builder.sample01.Matter;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class LevelOneCeiling implements Matter {

    @Override
    public String scene() { return "吊顶"; }

    @Override
    public String brand() { return "装修公司⾃带"; }

    @Override
    public String model() { return "⼀级顶"; }

    @Override
    public BigDecimal price() { return new BigDecimal(260); }

    @Override
    public String desc() { return "造型只做低⼀级，只有⼀个层次的吊顶，⼀般离顶120-150mm"; }

}
