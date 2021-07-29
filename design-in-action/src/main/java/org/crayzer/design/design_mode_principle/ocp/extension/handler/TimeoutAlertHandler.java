package org.crayzer.design.design_mode_principle.ocp.extension.handler;

import org.crayzer.design.design_mode_principle.ocp.AlertRule;
import org.crayzer.design.design_mode_principle.ocp.Notification;
import org.crayzer.design.design_mode_principle.ocp.NotificationEmergencyLevel;
import org.crayzer.design.design_mode_principle.ocp.extension.vo.ApiStatInfo;

public class TimeoutAlertHandler extends AlertHandler {

    public TimeoutAlertHandler(AlertRule rule, Notification notification) {
        super(rule, notification);
    }

    @Override
    public void check(ApiStatInfo apiStatInfo) {
        if (apiStatInfo.getTimeoutCount() > rule.getMatchedRule(apiStatInfo.getApi()).getMaxTimeoutCount()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "...");
        }
    }
}
