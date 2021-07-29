package org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v4;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.crayzer.design.design_mode_pattern.behavioural.observer.original.UserService;

import java.util.List;
import java.util.concurrent.Executors;

public class UserController {
    private UserService userService; // 依赖注入

    private EventBus eventBus;
    private static final int DEFAULT_EVENTBUS_THREAD_POOL_SIZE = 20;

    public UserController() {
        //eventBus = new EventBus(); // 同步阻塞模式
        eventBus = new AsyncEventBus(Executors.newFixedThreadPool(DEFAULT_EVENTBUS_THREAD_POOL_SIZE)); // 异步非阻塞模式
    }

    public void setRegObservers(List<Object> observers) {
        for (Object observer : observers) {
            eventBus.register(observer);
        }
    }

    public Long register(String telephone, String password) {
        //省略输入参数的校验代码
        //省略userService.register()异常的try-catch代码
        long userId = userService.register(telephone, password);

        // 调用 post() 函数发送消息的时候，并非把消息发送给所有的观察者，而是发送给可匹配的观察者。
        // 所谓可匹配指的是，能接收的消息类型是发送消息（post 函数定义中的 event）类型的父类。
        eventBus.post(userId);

        return userId;
    }
}

