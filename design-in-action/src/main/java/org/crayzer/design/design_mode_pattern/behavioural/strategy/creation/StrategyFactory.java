package org.crayzer.design.design_mode_pattern.behavioural.strategy.creation;


import org.crayzer.design.design_mode_pattern.behavioural.strategy.define.ConcreteStrategyA;
import org.crayzer.design.design_mode_pattern.behavioural.strategy.define.ConcreteStrategyB;
import org.crayzer.design.design_mode_pattern.behavioural.strategy.define.Strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略类是无状态的，不包含成员变量，只是纯碎的算法实现，可以共享
 */
class StrategyFactory1 {
    private static final Map<String, Strategy> strategies = new HashMap<>();

    static {
        strategies.put("A", new ConcreteStrategyA());
        strategies.put("B", new ConcreteStrategyB());
    }

    public static Strategy getStrategy(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        return strategies.get(type);
    }
}

/**
 * 策略类是有状态的，不可以共享，获得的都是新创建的策略对象
 */
class StrategyFactory2 {
    public static Strategy getStrategy(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }

        if (type.equals("A")) {
            return new ConcreteStrategyA();
        } else if (type.equals("B")) {
            return new ConcreteStrategyB();
        }

        return null;
    }
}
