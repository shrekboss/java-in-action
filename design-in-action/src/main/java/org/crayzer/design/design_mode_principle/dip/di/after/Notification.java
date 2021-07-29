package org.crayzer.design.design_mode_principle.dip.di.after;


import org.crayzer.design.design_mode_principle.dip.di.before.MessageSender;

/**
 * 依赖注入的实现方式
 */
public class Notification {
    private MessageSender messageSender;

    // 通过构造函数将messageSender传递进来
    public Notification(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendMessage(String cellphone, String message) {
        //...省略校验逻辑等...
        this.messageSender.send(cellphone, message);
    }
}
//使用Notification
// MessageSender messageSender = new MessageSender();
// Notification notification = new Notification(messageSender);

//使用Notification
// MessageSender messageSender = new SmsSender();
// Notification notification = new Notification(messageSender);
