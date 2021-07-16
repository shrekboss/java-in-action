package org.crayzer.conc.jdkconcurrentutil.completionservice;

import java.util.concurrent.*;

public class InquiryApplicationV2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /**
         * 不过在实际项目中，并不建议你这样做，因为 Java SDK 并发包里已经提供了设计精良的 CompletionService。
         *
         * 不同的是 CompletionService 是把任务执行结果的 Future 对象加入到阻塞队列中，而下面的示例代码是把任务最终的执行结果放入了阻塞队列中
         */

        // 创建阻塞队列
        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>();
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 异步向电商S1 | S2 | S3询价
        executor.submit(() -> {
            try {
                bq.put(getPriceByS1());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.submit(() -> {
            try {
                bq.put(getPriceByS2());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.submit(() -> {
            try {
                bq.put(getPriceByS3());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        for (int i = 0; i < 3; i++) {
            Integer r = bq.take();
            executor.execute(() -> save(r));
        }
    }

    private static void save(Integer r) {}

    private static Integer getPriceByS3() {
        return null;
    }

    private static Integer getPriceByS2() {
        return null;
    }

    private static Integer getPriceByS1() {
        return null;
    }
}
