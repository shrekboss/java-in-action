package org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.lookupTable;

import org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.branchingLogic.State;

public class ApplicationDemo {
    public static void main(String[] args) {
        MarioStateMachine mario = new MarioStateMachine();
        mario.obtainMushRoom();
        int score = mario.getScore();
        State state = mario.getCurrentState();
        System.out.println("mario score: " + score + "; state: " + state);
    }
}
