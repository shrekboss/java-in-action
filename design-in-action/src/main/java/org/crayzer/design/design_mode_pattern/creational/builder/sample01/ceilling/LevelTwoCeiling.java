package org.crayzer.design.design_mode_pattern.creational.builder.sample01.ceilling;

import org.crayzer.design.design_mode_pattern.creational.builder.sample01.Matter;

import java.math.BigDecimal;

/**
 * @author yizhe.chen
 */
public class LevelTwoCeiling implements Matter {

    @Override
    public String scene() {
        return "吊顶";
    }

    @Override
    public String brand() {
        return "装修公司⾃带";
    }

    @Override
    public String model() {
        return "⼆级顶";
    }

    @Override
    public BigDecimal price() {
        return new BigDecimal(850);
    }

    @Override
    public String desc() { return "两个层次的吊顶，⼆级吊顶⾼度⼀般就往下吊20cm，要是层⾼很⾼，也可增加 每级的厚度"; }
}
