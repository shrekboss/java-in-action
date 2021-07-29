package org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v3;

import org.crayzer.design.design_mode_pattern.behavioural.observer.original.UserService;
import org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v2.RegObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

// 第二种实现方式，其他类代码不变，就没有再重复罗列
// 第二种实现方式，尽管利用了线程池解决了第一种实现方式的问题，但线程池、异步执行逻辑都耦合在了
// register() 函数中，增加了这部分业务代码的维护成本。
@Deprecated
public class UserController {
    private UserService userService; // 依赖注入
    private List<RegObserver> regObservers = new ArrayList<>();
    private Executor executor;

    public UserController(Executor executor) {
        this.executor = executor;
    }

    public void setRegObservers(List<RegObserver> observers) {
        regObservers.addAll(observers);
    }

    public Long register(String telephone, String password) {
        //省略输入参数的校验代码
        //省略userService.register()异常的try-catch代码
        long userId = userService.register(telephone, password);

        for (RegObserver observer : regObservers) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    observer.handleRegSuccess(userId);
                }
            });
        }

        return userId;
    }
}
