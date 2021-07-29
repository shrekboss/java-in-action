package org.crayzer.design.design_mode_pattern.behavioural.memento.original;

import java.util.Stack;

/**
 * 2. 快照本身是不可变的，理论上讲，不应该包含任何 set() 等修改内部状态的函数，“快照“这个业务模型复用
 * 了 InputText 类的定义，而 InputText 类本身有一系列修改内部状态的函数，所以，用 InputText 类来
 * 表示快照违背了封装原则。
 */
public class SnapshotHolder {
    private Stack<InputText> snapshots = new Stack<>();

    public InputText popSnapshot() {
        return snapshots.pop();
    }

    public void pushSnapshot(InputText inputText) {
        InputText deepClonedInputText = new InputText();
        deepClonedInputText.setText(inputText.getText());
        snapshots.push(deepClonedInputText);
    }
}
