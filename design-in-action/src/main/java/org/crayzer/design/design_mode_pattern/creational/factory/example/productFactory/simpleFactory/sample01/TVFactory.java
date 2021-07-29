package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.simpleFactory.sample01;

import org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.HaierTV;
import org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.HisenseTV;
import org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.TV;

public class TVFactory {
    public static TV produceTV(String brand) {
        if ("Hisense".equalsIgnoreCase(brand)) {
            return new HisenseTV();
        } else if ("Haier".equalsIgnoreCase(brand)) {
            return new HaierTV();
        } else {
            System.out.println("本工厂无法提高您所需的产品！");
            return null;
        }
    }
}
