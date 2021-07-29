package org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v3;


import org.crayzer.design.design_mode_pattern.behavioural.observer.original.PromotionService;
import org.crayzer.design.design_mode_pattern.behavioural.observer.refactor.v2.RegObserver;

// 第一种实现方式，其他类代码不变，就没有再重复罗列
// 对于第一种实现方式，频繁地创建和销毁线程比较耗时，并且并发线程数无法控制，创建过多的线程会导
// 致堆栈溢出。
@Deprecated
public class RegPromotionObserver implements RegObserver {
    private PromotionService promotionService; // 依赖注入

    @Override
    public void handleRegSuccess(long userId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                promotionService.issueNewUserExperienceCash(userId);
            }
        });
        thread.start();
    }
}

