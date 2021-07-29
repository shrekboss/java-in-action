package org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v2;

import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RequestStat;

import java.util.Map;

public interface StatViewer {
    void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills);
}
