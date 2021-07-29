package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04;

public class Order {
    private String uid;
    private double amount;
    private String orderId;

    public Order(String uid, double amount, String orderId) {
        this.uid = uid;
        this.amount = amount;
        this.orderId = orderId;
    }

    public PayState pay(PayType payType){
        return payType.get().pay(this.uid,this.amount);
    }

}
