package org.crayzer.design.design_mode_principle.inaciton.performanceCounter;

import lombok.Data;

@Data
public class RequestInfo {
    private String apiName;
    private double responseTime;
    private long timestamp;

    public RequestInfo(String apiName, double responseTime, long timestamp) {
        this.apiName = apiName;
        this.responseTime = responseTime;
        this.timestamp = timestamp;
    }
}
