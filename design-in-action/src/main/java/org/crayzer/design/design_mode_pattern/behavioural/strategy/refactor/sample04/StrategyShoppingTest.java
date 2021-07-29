package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04;

public class StrategyShoppingTest {
    public static void main(String[] args) {
        Order order = new Order("shrekboss", 345.12, "2018031200009000011");
        System.out.println(order.pay(PayType.UNION_PAY));
    }
}
