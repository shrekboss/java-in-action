package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04;

import org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04.thirdPartyPayment.*;

public enum PayType {
    ALI_PAY(new AliPay()),
    WECHAT_PAY(new WechatPay()),
    UNION_PAY(new UnionPay()),
    JD_PAY(new JDPay());

    private Payment payment;

    PayType(Payment payment) {
        this.payment = payment;
    }

    public Payment get() {
        return this.payment;
    }
}
