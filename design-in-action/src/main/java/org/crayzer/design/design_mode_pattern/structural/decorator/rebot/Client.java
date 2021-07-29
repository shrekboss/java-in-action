package org.crayzer.design.design_mode_pattern.structural.decorator.rebot;

public class Client {
    public static void main(String[] args) {
        Transform camaro = new Car();
        camaro.move();

        Airplane airplane = new Airplane(camaro);
        airplane.fly();

        Robot robot = new Robot(camaro);
        robot.say();
    }
}
