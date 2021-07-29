package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04.thirdPartyPayment;

import org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample04.PayState;

public interface Payment {
    PayState pay(String uid, double amount);
}
