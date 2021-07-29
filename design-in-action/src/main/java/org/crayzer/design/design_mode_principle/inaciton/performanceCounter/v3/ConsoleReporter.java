package org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v3;


import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.MetricsStorage;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RedisMetricsStorage;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RequestInfo;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RequestStat;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v2.Aggregator;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v2.ConsoleViewer;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v2.StatViewer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleReporter extends ScheduledReporter {
    private ScheduledExecutorService executor;
    private Runnable runnable;

    // 兼顾代码的易用性，新增一个封装了默认依赖的构造函数
    public ConsoleReporter() {
        this(new RedisMetricsStorage(), new Aggregator(), new ConsoleViewer());
    }

    // 兼顾灵活性和代码的可测试性，这个构造函数继续保留
    public ConsoleReporter(MetricsStorage metricsStorage, Aggregator aggregator, StatViewer viewer) {
        super(metricsStorage, aggregator, viewer);
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    // 第4个代码逻辑：定时触发第1、2、3代码逻辑的执行；
    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        // 添加判断 防止重复调用
        if (runnable != null) {
            System.out.println("duplicate calls!");
            return;
        }
        runnable = () -> {
            long durationInMillis = durationInSeconds * 1000;
            long endTimeInMillis = System.currentTimeMillis();
            long startTimeInMillis = endTimeInMillis - durationInMillis;
            Map<String, List<RequestInfo>> requestInfos = metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
            Map<String, RequestStat> requestStats = aggregator.aggregate(requestInfos, durationInMillis);
            viewer.output(requestStats, startTimeInMillis, endTimeInMillis);
        };
        executor.scheduleAtFixedRate(runnable, 0, periodInSeconds, TimeUnit.SECONDS);
    }
}
