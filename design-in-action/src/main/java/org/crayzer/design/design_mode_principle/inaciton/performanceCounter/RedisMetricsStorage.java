package org.crayzer.design.design_mode_principle.inaciton.performanceCounter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RedisMetricsStorage implements MetricsStorage {
    //...省略属性和构造函数等...
    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {
        //...
        System.out.println("保存数据");
    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
        //...
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {
        //...
        return Collections.emptyMap();
    }
}