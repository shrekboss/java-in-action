package org.crayzer.design.design_mode_pattern.behavioural.template.sample01;

public class BootStrap {
    public static void main(String[] args) {
        BankTemplateMethod templateMethod = new Transfer();
        templateMethod.process();
    }
}
