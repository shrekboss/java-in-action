package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.abstractFactory;

public interface EFactory {
    Television produceTelevision();

    AirConditioner produceAirConditioner();
}