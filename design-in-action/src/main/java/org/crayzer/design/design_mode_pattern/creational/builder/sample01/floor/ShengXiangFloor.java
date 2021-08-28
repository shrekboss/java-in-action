package org.crayzer.design.design_mode_pattern.creational.builder.sample01.floor;

import org.crayzer.design.design_mode_pattern.creational.builder.sample01.Matter;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class ShengXiangFloor implements Matter {
    @Override
    public String scene() {
        return "地板";
    }

    @Override
    public String brand() { return "圣象"; }

    @Override
    public String model() { return "⼀级"; }

    @Override
    public BigDecimal price() { return new BigDecimal(318); }

    @Override
    public String desc() { return "圣象地板是中国地板⾏业著名品牌。圣象地板拥有中国驰名商标、中国名牌、国 家免检、中国环境标志认证等多项荣誉。"; }
}
