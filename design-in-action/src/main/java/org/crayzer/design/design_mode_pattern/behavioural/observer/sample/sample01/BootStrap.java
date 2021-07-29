package org.crayzer.design.design_mode_pattern.behavioural.observer.sample.sample01;

public class BootStrap {
    public static void main(String[] args) {
        MySubject cat = new Cat();

        MyObserver obs1, obs2, obs3, obs4;

        obs1 = new Mouse();
        obs2 = new Mouse();
        obs3 = new Dog();
        obs4 = new Pig();

        cat.attach(obs1);
        cat.attach(obs2);
        cat.attach(obs3);
        cat.attach(obs4);

        cat.cry();
    }
}
