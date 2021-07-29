package org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v4;

import com.google.common.eventbus.Subscribe;
import org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v2.NotificationService;

/**
 * 不需要定义 Observer 接口，任意类型的对象都可以注册到 EventBus 中，通过 @Subscribe 注解来标明类
 * 中哪个函数可以接收被观察者发送的消息。
 */
public class RegNotificationObserver {
    private NotificationService notificationService;

    @Subscribe
    public void handleRegSuccess(long userId) {
        notificationService.sendInboxMessage(userId, "...");
    }
}
