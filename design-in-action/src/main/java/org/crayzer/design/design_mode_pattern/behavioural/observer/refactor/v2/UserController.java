package org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v2;

import org.crayzer.design.design_mode_pattern.behavioural.observer.original.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步阻塞观察者模式
 */
public class UserController {
    private UserService userService; // 依赖注入
    private List<RegObserver> regObservers = new ArrayList<>();

    // 一次性设置好，之后也不可能动态的修改
    public void setRegObservers(List<RegObserver> observers) {
        regObservers.addAll(observers);
    }

    public Long register(String telephone, String password) {
        //省略输入参数的校验代码
        //省略userService.register()异常的try-catch代码
        long userId = userService.register(telephone, password);

        for (RegObserver observer : regObservers) {
            observer.handleRegSuccess(userId);
        }

        return userId;
    }
}
