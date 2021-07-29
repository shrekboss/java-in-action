package org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.state.v1;


import org.crayzer.design.design_mode_pattern.behavioural.state.stateMachine.branchingLogic.State;

public interface IMario { //所有状态类的接口
    State getName();
    //以下是定义的事件
    void obtainMushRoom();
    void obtainCape();
    void obtainFireFlower();
    void meetMonster();
}

// 省略CapeMario、FireMario类...

