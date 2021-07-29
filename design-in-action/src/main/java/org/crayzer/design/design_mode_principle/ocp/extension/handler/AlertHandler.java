package org.crayzer.design.design_mode_principle.ocp.extension.handler;

import org.crayzer.design.design_mode_principle.ocp.AlertRule;
import org.crayzer.design.design_mode_principle.ocp.Notification;
import org.crayzer.design.design_mode_principle.ocp.extension.vo.ApiStatInfo;

public abstract class AlertHandler {
    protected AlertRule rule;
    protected Notification notification;
    public AlertHandler(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }
    public abstract void check(ApiStatInfo apiStatInfo);
}
