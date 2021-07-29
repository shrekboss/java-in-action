package org.crayzer.design.design_mode_pattern.behavioural.strategy.refactor.sample01;

import org.crayzer.design.design_mode_pattern.behavioural.strategy.original.sample01.OrderType;

import java.util.HashMap;
import java.util.Map;

// 策略的创建(无状态)
class DiscountStrategyFactory1 {
    private static final Map<OrderType, DiscountStrategy> strategies = new HashMap<>();

    static {
        strategies.put(OrderType.NORMAL, new NormalDiscountStrategy());
        strategies.put(OrderType.GROUPON, new GrouponDiscountStrategy());
        strategies.put(OrderType.PROMOTION, new PromotionDiscountStrategy());
    }

    public static DiscountStrategy getDiscountStrategy(OrderType type) {
        return strategies.get(type);
    }
}

// 策略的创建(有状态)
class DiscountStrategyFactory2 {
    public static DiscountStrategy getDiscountStrategy(OrderType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type should not be null.");
        }
        if (type.equals(OrderType.NORMAL)) {
            return new NormalDiscountStrategy();
        } else if (type.equals(OrderType.GROUPON)) {
            return new GrouponDiscountStrategy();
        } else if (type.equals(OrderType.PROMOTION)) {
            return new PromotionDiscountStrategy();
        }
        return null;
    }
}
