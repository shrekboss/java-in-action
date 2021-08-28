package org.crayzer.design.design_mode_pattern.creational.builder.sample01.coat;

import org.crayzer.design.design_mode_pattern.creational.builder.sample01.Matter;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class LiBangCoat implements Matter {
    @Override
    public String scene() { return "涂料"; }

    @Override
    public String brand() { return "立邦"; }

    @Override
    public String model() { return "默认级别"; }

    @Override
    public BigDecimal price() { return new BigDecimal(650); }

    @Override
    public String desc() { return "⽴邦始终以开发绿⾊产品、注重⾼科技、⾼品质为⽬标，以技术⼒量不断推进科 研和开发，满⾜消费者需求。"; }
}
