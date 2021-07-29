package org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v2;

import com.google.gson.Gson;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RequestStat;

import java.util.Map;


public class ConsoleViewer implements StatViewer {
    @Override
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills) {
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMills + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(requestStats));
    }
}


