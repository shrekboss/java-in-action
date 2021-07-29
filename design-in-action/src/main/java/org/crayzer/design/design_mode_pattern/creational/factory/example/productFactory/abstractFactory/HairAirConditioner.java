package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.abstractFactory;

public class HairAirConditioner implements AirConditioner {
    public void changeTemperature() {
        System.out.println("海尔空调温度改变中......");
    }
}