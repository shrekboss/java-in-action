package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.abstractFactory;

public class HaierTelevision implements Television {
    @Override
    public void play() {
        System.out.println("海尔电视机播放中......");
    }
}