package org.crayzer.design.design_mode_pattern.structural.bridge.refactor;

public class NormalNotification extends Notification {
    public NormalNotification(MsgSender msgSender) {
        super(msgSender);
    }

    @Override
    public void notify(String message) {
        msgSender.send(message);
    }
    // 与SevereNotification代码结构类似，所以省略...
}
