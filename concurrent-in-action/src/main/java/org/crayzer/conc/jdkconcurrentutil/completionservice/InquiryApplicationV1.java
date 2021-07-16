package org.crayzer.conc.jdkconcurrentutil.completionservice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InquiryApplicationV1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Integer r;
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 异步向电商S1询价
        Future<Integer> f1 = executor.submit(() -> getPriceByS1());
        // 异步向电商S2询价
        Future<Integer> f2 = executor.submit(() -> getPriceByS2());
        // 异步向电商S3询价
        Future<Integer> f3 = executor.submit(() -> getPriceByS3());

        /**
         * 如果获取电商 S1 报价的耗时很长，那么即便获取电商 S2 报价的耗时很短，
         * 也无法让保存 S2 报价的操作先执行，因为这个主线程都阻塞在了 f1.get() 操作上
         */
        // 获取电商S1报价并保存
        Integer r1 = f1.get();
        executor.execute(() -> save(r1));
        // 获取电商S2报价并保存
        Integer r2 = f2.get();
        executor.execute(() -> save(r2));
        // 获取电商S1报价并保存
        Integer r3 = f3.get();
        executor.execute(() -> save(r3));
    }

    private static void save(Integer r) {}

    private static Integer getPriceByS3() {
        return null;
    }
    private static Integer getPriceByS2() {
        return null;
    }
    private static Integer getPriceByS1() { return null; }
}
