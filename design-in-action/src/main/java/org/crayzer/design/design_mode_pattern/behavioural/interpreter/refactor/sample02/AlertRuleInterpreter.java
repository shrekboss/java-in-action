package org.crayzer.design.design_mode_pattern.behavioural.interpreter.refactor.sample02;

import java.util.Map;

public class AlertRuleInterpreter {
    private Expression expression;

    public AlertRuleInterpreter(String ruleExpression) {
        this.expression = new OrExpression(ruleExpression);
    }

    public boolean interpret(Map<String, Long> stats) {
        return expression.interpret(stats);
    }
}
