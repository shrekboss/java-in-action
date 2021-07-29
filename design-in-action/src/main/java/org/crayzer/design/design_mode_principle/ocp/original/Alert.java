package org.crayzer.design.design_mode_principle.ocp.original;


import org.crayzer.design.design_mode_principle.ocp.AlertRule;
import org.crayzer.design.design_mode_principle.ocp.Notification;
import org.crayzer.design.design_mode_principle.ocp.NotificationEmergencyLevel;

public class Alert {
    private AlertRule rule;
    private Notification notification;

    public Alert(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    // 改动一：添加参数timeoutCount
    public void check(String api, long requestCount, long errorCount, long timeoutCount, long durationOfSeconds) {
        // 当接口的 TPS 超过某个预先设置的最大值时，以及当接口请求出错数大于某个最大允许值时，
        // 就会触发告警，通知接口的相关负责人或者团队
        long tps = requestCount / durationOfSeconds;
        if (tps > rule.getMatchedRule(api).getMaxTps()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "...");
        }

        /* 新增需求 */
        // 当每秒钟接口超时请求个数，超过某个预先设置的最大阈值时，我们也要触发告警发送通知
        if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
            notification.notify(NotificationEmergencyLevel.SEVERE, "...");
        }

        // 改动二：添加接口超时处理逻辑
        long timeoutTps = timeoutCount / durationOfSeconds;
        if (timeoutTps > rule.getMatchedRule(api).getMaxTimeoutTps()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "...");
        }
    }
}
