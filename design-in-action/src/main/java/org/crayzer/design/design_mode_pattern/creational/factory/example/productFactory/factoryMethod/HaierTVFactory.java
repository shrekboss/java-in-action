package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.factoryMethod;

import org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.HaierTV;
import org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.TV;

public class HaierTVFactory implements TVFactory {
    @Override
    public TV produceTV() {
        System.out.println("海尔电视机工厂生产海尔电视机。");
        return new HaierTV();
    }
}
