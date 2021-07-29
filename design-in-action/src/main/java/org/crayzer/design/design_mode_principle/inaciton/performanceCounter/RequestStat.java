package org.crayzer.design.design_mode_principle.inaciton.performanceCounter;

import lombok.Data;

@Data
public class RequestStat {
    private double maxResponseTime;
    private double minResponseTime;
    private double avgResponseTime;
    private double p999ResponseTime;
    private double p99ResponseTime;
    private long count;
    private double tps;
}
