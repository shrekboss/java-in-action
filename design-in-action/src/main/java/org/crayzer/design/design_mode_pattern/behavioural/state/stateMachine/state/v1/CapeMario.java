package org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.state.v1;

import org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.branchingLogic.State;

public class CapeMario implements IMario {
    private MarioStateMachine stateMachine;

    public CapeMario(MarioStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public State getName() {
        return State.CAPE;
    }

    @Override
    public void obtainMushRoom() {
    }

    @Override
    public void obtainCape() {
    }

    @Override
    public void obtainFireFlower() {
    }

    @Override
    public void meetMonster() {
        stateMachine.setCurrentState(new SmallMario(stateMachine));
        stateMachine.setScore(stateMachine.getScore() - 200);
    }
}
