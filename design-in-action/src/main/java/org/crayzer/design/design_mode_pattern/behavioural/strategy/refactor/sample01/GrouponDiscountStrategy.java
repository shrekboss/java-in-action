package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample01;

import org.crayzer.design.design_mode_pattern.behavioural.strategy.original.sample01.Order;

public class GrouponDiscountStrategy implements DiscountStrategy {
    @Override
    public double calDiscount(Order order) {
        return 0;
    }
}
