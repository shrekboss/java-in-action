package org.crayzer.design.design_mode_principle.inaciton.performanceCounter;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executors;

public class MetricsCollector {
    // private MetricsStorage metricsStorage;//基于接口而非实现编程
    //
    // //依赖注入
    // public MetricsCollector(MetricsStorage metricsStorage) {
    //     this.metricsStorage = metricsStorage;
    // }
    //
    // public MetricsCollector() {
    //     this(new RedisMetricsStorage());
    // }
    //
    // //用一个函数代替了最小原型中的两个函数
    // public void recordRequest(RequestInfo requestInfo) {
    //     if (requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
    //         return;
    //     }
    //     metricsStorage.saveRequestInfo(requestInfo);
    // }

    /**
     * version 3
     */
    private static final int DEFAULT_STORAGE_THREAD_POOL_SIZE = 20;
    private MetricsStorage metricsStorage;
    private EventBus eventBus;

    public MetricsCollector() {
        this(new RedisMetricsStorage());
    }

    public MetricsCollector(MetricsStorage metricsStorage) {
        this(metricsStorage, DEFAULT_STORAGE_THREAD_POOL_SIZE);
    }

    public MetricsCollector(MetricsStorage metricsStorage, int threadNumToSaveData) {
        this.metricsStorage = metricsStorage;
        this.eventBus = new AsyncEventBus(Executors.newFixedThreadPool(threadNumToSaveData));
        this.eventBus.register(new EventListener());
    }

    public void recordRequest(RequestInfo requestInfo) {
        if (requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
            return;
        }
        eventBus.post(requestInfo);
    }

    public class EventListener {
        @Subscribe
        public void saveRequestInfo(RequestInfo requestInfo) {
            metricsStorage.saveRequestInfo(requestInfo);
        }
    }
}


