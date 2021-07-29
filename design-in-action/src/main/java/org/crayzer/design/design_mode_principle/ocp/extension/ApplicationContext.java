package org.crayzer.design.design_mode_principle.ocp.extension;


import org.crayzer.design.design_mode_principle.ocp.AlertRule;
import org.crayzer.design.design_mode_principle.ocp.Notification;
import org.crayzer.design.design_mode_principle.ocp.extension.handler.ErrorAlertHandler;
import org.crayzer.design.design_mode_principle.ocp.extension.handler.TimeoutAlertHandler;
import org.crayzer.design.design_mode_principle.ocp.extension.handler.TpsAlertHandler;

public class ApplicationContext {
    private AlertRule alertRule;
    private Notification notification;
    private Alert alert;

    public void initializeBeans() {
        alertRule = new AlertRule(/*.省略参数.*/); //省略一些初始化代码
        notification = new Notification(/*.省略参数.*/); //省略一些初始化代码
        alert = new Alert();
        alert.addAlertHandler(new TpsAlertHandler(alertRule, notification));
        alert.addAlertHandler(new ErrorAlertHandler(alertRule, notification));
        alert.addAlertHandler(new TimeoutAlertHandler(alertRule, notification));
    }
    public Alert getAlert() { return alert; }

    // 饿汉式单例
    private static final ApplicationContext instance = new ApplicationContext();
    private ApplicationContext() {
        // final声明可以直接初始化，也就是调用空参，然而空参构造里又需要还未初始化成功的对象引用，
        // instance，所以会空指针
        // instance.initializeBeans();
        this.initializeBeans();
    }
    public static ApplicationContext getInstance() {
        return instance;
    }
}
