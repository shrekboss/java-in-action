package org.crayzer.design.design_mode_pattern.creational.builder.sample01.floor;

import org.crayzer.design.design_mode_pattern.creational.builder.sample01.Matter;

import java.math.BigDecimal;

/**
 * @author yizhe.chen
 */
public class DerFloor implements Matter {
    @Override
    public String scene() { return "地板"; }

    @Override
    public String brand() { return "德尔(Der)"; }

    @Override
    public String model() { return "A+"; }

    @Override
    public BigDecimal price() { return new BigDecimal(119); }

    @Override
    public String desc() { return "DER德尔集团是全球领先的专业⽊地板制造商，北京2008年奥运会家装和公装 地板供应商"; }
}
