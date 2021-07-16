package org.crayzer.conc.design.pattern.producerconsumer.monitoringsystem;

import javafx.concurrent.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitoringSystem {
    private BlockingQueue<Task> bq = new LinkedBlockingQueue<>(2000);

    public void doSaveMonitorIndex() {
    }

    void start() {
        ExecutorService es = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {

            es.execute(() -> {
                try {
                    while (true) {
                        List<Task> ts = pollTask();
                        execTasks(ts);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void execTasks(List<Task> ts) {
        //todo
    }

    private List<Task> pollTask() throws InterruptedException{
        List<Task> ts = new LinkedList<>();
        //阻塞式获取一条任务
        Task t = bq.take();
        while (t != null) {
            ts.add(t);
            // 非阻塞式获取一条任务
            t = bq.poll();
        }
        return ts;
    }
}
