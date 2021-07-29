package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.abstractFactory;

public class HaierFactory implements EFactory {
    @Override
    public Television produceTelevision() {
        return new HaierTelevision();
    }

    @Override
    public AirConditioner produceAirConditioner() {
        return new HairAirConditioner();
    }
}