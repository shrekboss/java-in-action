package org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.state.v1;

import org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.branchingLogic.State;

public class MarioStateMachine {
    private int score;
    private IMario currentState; // 不再使用枚举来表示状态

    public MarioStateMachine() {
        this.score = 0;
        this.currentState = new SmallMario(this);
    }

    public void obtainMushRoom() {
        this.currentState.obtainMushRoom();
    }

    public void obtainCape() {
        this.currentState.obtainCape();
    }

    public void obtainFireFlower() {
        this.currentState.obtainFireFlower();
    }

    public void meetMonster() {
        this.currentState.meetMonster();
    }

    public int getScore() {
        return this.score;
    }

    public State getCurrentState() {
        return this.currentState.getName();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCurrentState(IMario currentState) {
        this.currentState = currentState;
    }
}
