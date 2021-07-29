package org.crayzer.design.design_mode_pattern.behavioural.template.sample01;

public class Transfer extends BankTemplateMethod{
    @Override
    public void transact() {
        System.out.println("转账");
    }
}
