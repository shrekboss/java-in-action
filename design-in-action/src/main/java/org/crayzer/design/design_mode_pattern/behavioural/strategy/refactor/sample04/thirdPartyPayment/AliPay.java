package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04.thirdPartyPayment;

import org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04.PayState;

public class AliPay implements Payment{
    @Override
    public PayState pay(String uid, double amount) {
        System.out.println("欢迎使用支付宝支付");
        System.out.println("直接从花呗扣款");
        return new PayState(200, uid + "支付成功", amount);
    }
}
