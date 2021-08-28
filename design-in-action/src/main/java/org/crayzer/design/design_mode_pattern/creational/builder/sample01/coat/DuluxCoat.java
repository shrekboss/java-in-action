package org.crayzer.design.design_mode_pattern.creational.builder.sample01.coat;


import org.crayzer.design.design_mode_pattern.creational.builder.sample01.Matter;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class DuluxCoat implements Matter {
    @Override
    public String scene() { return "涂料"; }

    @Override
    public String brand() { return "多乐⼠(Dulux)"; }

    @Override
    public String model() { return "第⼆代"; }

    @Override
    public BigDecimal price() { return new BigDecimal(719); }

    @Override
    public String desc() { return "多乐⼠是阿克苏诺⻉贝尔旗下的著名建筑装饰油漆品牌，产品畅销于全球100个国家，每年全球有5000万户家庭使⽤多乐⼠油漆。"; }
}
