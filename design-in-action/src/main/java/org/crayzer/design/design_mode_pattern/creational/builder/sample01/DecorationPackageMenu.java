package org.crayzer.design.design_mode_pattern.creational.builder.sample01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yizhe.chen
 */
public class DecorationPackageMenu implements IMenu {

    private List<Matter> list = new ArrayList<>();
    private BigDecimal price = BigDecimal.ZERO;

    // 面积
    private BigDecimal area;
    // 装修等级；豪华欧式、轻奢⽥园、现代简约
    private String grade;

    private DecorationPackageMenu() {
    }

    public DecorationPackageMenu(Double area, String grade) {
        this.area = new BigDecimal(area);
        this.grade = grade;
    }

    @Override
    public IMenu appendCeiling(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(new BigDecimal("0.2")).multiply(matter.price()));
        return this;
    }

    @Override
    public IMenu appendCoat(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(new BigDecimal("1.4")).multiply(matter.price()));
        return this;
    }

    @Override
    public IMenu appendFloor(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(matter.price()));
        return this;
    }

    @Override
    public IMenu appendTile(Matter matter) {
        list.add(matter);
        price = price.add(area.multiply(matter.price()));
        return this;
    }

    @Override
    public String getDetail() {

        StringBuilder detail = new StringBuilder("\r\n------------------------------------------------------\r\n" +
                "装修清单" + "\r\n" +
                "套餐等级：" + grade + "\r\n" +
                "套餐价格：" + price.setScale(2, BigDecimal.ROUND_HALF_UP) + " 元\r\n" +
                "房屋⾯积：" + area.doubleValue() + " 平⽶\r\n" +
                "材料清单：\r\n");

        for (Matter matter : list) {
            detail.append(matter.scene()).append("：").append(matter.brand()).append("、").append(matter.model()).append("、平⽶价格：").append(matter.price()).append(" 元。\n");
        }
        return detail.toString();
    }
}