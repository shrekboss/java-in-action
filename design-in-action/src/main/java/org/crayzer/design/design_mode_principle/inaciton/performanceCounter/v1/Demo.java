package org.crayzer.design.design_mode_principle.inaciton.performanceCounter.v1;


import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.MetricsCollector;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.MetricsStorage;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RedisMetricsStorage;
import org.crayzer.design.design_mode_principle.inaciton.performanceCounter.RequestInfo;

public class Demo {
    public static void main(String[] args) {
        MetricsStorage storage = new RedisMetricsStorage();
        ConsoleReporter consoleReporter = new ConsoleReporter(storage);
        consoleReporter.startRepeatedReport(60, 60);

        EmailReporter emailReporter = new EmailReporter(storage);
        emailReporter.addToAddress("wangzheng@xzg.com");
        emailReporter.startDailyReport();

        MetricsCollector collector = new MetricsCollector(storage);
        collector.recordRequest(new RequestInfo("register", 123, 10234));
        collector.recordRequest(new RequestInfo("register", 223, 11234));
        collector.recordRequest(new RequestInfo("register", 323, 12334));
        collector.recordRequest(new RequestInfo("login", 23, 12434));
        collector.recordRequest(new RequestInfo("login", 1223, 14234));

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
