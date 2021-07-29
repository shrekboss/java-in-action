package org.crayzer.design.design_mode_principle.ocp.extension.vo;

import lombok.Data;

@Data
public class ApiStatInfo {
    private String api;
    private long requestCount;
    private long errorCount;
    private long durationOfSeconds;
    private long timeoutCount;
}
