package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample01;


import org.crayzer.design.design_mode_pattern.behavioural.strategy.original.sample01.Order;

// 策略的定义
public interface DiscountStrategy {
    double calDiscount(Order order);
}
// 省略NormalDiscountStrategy、GrouponDiscountStrategy、PromotionDiscountStrategy类代码...

