package org.crayzer.design.design_mode_pattern.creational.builder.sample01.tile;

import org.crayzer.design.design_mode_pattern.creational.builder.sample01.Matter;

import java.math.BigDecimal;

/**
 * @author yizhe.chen
 */
public class MarcoPoloTile implements Matter {

    @Override
    public String scene() { return "地砖"; }

    @Override
    public String brand() { return "⻢马可波罗(MARCO POLO)"; }

    @Override
    public String model() { return "缺省"; }

    @Override
    public BigDecimal price() { return new BigDecimal(140); }

    @Override
    public String desc() { return "“⻢马可波罗”品牌诞⽣于1996年，作为国内最早品牌化的建陶品牌，以“⽂化陶 瓷”占领市场，享有“仿古砖⾄尊”的美誉。"; }
}
