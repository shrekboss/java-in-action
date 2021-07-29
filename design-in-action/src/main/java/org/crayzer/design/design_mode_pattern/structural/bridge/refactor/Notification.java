package org.crayzer.design.design_mode_pattern.structural.bridge.refactor;

/**
 * Notification 类相当于抽象，MsgSender 类相当于实现，两者可以独立开发，通过组合关系（也就是桥梁）
 * 任意组合在一起。所谓任意组合的意思就是，不同紧急程度的消息和发送渠道之间的对应关系，不是在代码
 * 中固定写死的，可以动态地去指定（比如，通过读取配置来获取对应关系）。
 */
public abstract class Notification {
    protected MsgSender msgSender;

    public Notification(MsgSender msgSender) {
        this.msgSender = msgSender;
    }

    public abstract void notify(String message);
}
