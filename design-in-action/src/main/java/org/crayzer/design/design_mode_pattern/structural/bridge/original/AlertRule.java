package org.crayzer.design.design_mode_pattern.structural.bridge.original;

public class AlertRule {

    private long maxTps;
    private long maxErrorCount;
    private long maxTimeoutCount;
    private long maxTimeoutTps;

    public AlertRule() {
    }

    public AlertRule(long maxTps, long maxErrorCount, long maxTimeoutCount, long maxTimeoutTps) {
        this.maxTps = maxTps;
        this.maxErrorCount = maxErrorCount;
        this.maxTimeoutCount = maxTimeoutCount;
        this.maxTimeoutTps = maxTimeoutTps;
    }

    public AlertRule getMatchedRule(String api) {
        return this;
    }

    public long getMaxTps() {
        return maxTps;
    }

    public void setMaxTps(long maxTps) {
        this.maxTps = maxTps;
    }

    public long getMaxErrorCount() {
        return maxErrorCount;
    }

    public void setMaxErrorCount(long maxErrorCount) {
        this.maxErrorCount = maxErrorCount;
    }

    public long getMaxTimeoutCount() {
        return maxTimeoutCount;
    }

    public void setMaxTimeoutCount(long maxTimeoutCount) {
        this.maxTimeoutCount = maxTimeoutCount;
    }

    public long getMaxTimeoutTps() {
        return maxTimeoutTps;
    }
}
