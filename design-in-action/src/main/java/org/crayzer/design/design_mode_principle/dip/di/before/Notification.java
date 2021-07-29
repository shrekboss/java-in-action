package org.crayzer.design.design_mode_principle.dip.di.before;


/**
 * 非依赖注入实现方式
 */
public class Notification {
    private MessageSender messageSender;

    public Notification() {
        //此处有点像hardcode
        this.messageSender = new MessageSender();
    }

    public void sendMessage(String cellphone, String message) {
        //...省略校验逻辑等...
        this.messageSender.send(cellphone, message);
    }
}
// 使用Notification
//Notification notification = new Notification();
