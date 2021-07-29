package org.crayzer.design.design_mode_pattern.behavioural.interpreter.refactor.sample01;

// 这里就省略了
public class SubstractionExpression implements Expression {
    private Expression exp1;
    private Expression exp2;

    public SubstractionExpression(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public long interpret() {
        return exp1.interpret() - exp2.interpret();
    }
}
