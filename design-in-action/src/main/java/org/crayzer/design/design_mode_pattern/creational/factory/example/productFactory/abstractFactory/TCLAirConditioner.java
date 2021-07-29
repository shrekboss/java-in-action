package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.abstractFactory;

import java.util.ArrayList;
import java.util.List;

public class TCLAirConditioner implements AirConditioner {
    @Override
    public void changeTemperature() {
        System.out.println("TCL空调温度改变中......");
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        System.out.println(list.toString());
    }
}