package org.crayzer.design.design_mode_pattern.behavioural.template.sample01;

public abstract class BankTemplateMethod {
    public void process() {
        this.tableNumber();
        this.transact();
        this.evaluate();
    }

    public void evaluate() {
        System.out.println("反馈评分。");
    }

    public abstract void transact();

    public void tableNumber() {
        System.out.println("取号排队@！");
    }
}
