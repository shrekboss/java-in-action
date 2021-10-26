package org.crayzer.conc.juc.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class InquiryApplicationAfter {

    public static AtomicReference<Integer> inquiry() throws InterruptedException, ExecutionException {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);
        List<Future<Integer>> futures = new ArrayList<>(3);
        futures.add(cs.submit(() -> getPriceByS1()));
        futures.add(cs.submit(() -> getPriceByS2()));
        futures.add(cs.submit(() -> getPriceByS3()));
        // 获取最快返回的任务执行结果
        AtomicReference<Integer> m = new AtomicReference<>(Integer.MAX_VALUE);
        CountDownLatch latch = new CountDownLatch(3);
        // 只要有一个成功返回，则break
        for (int i = 0; i < 3; i++) {
            // r = cs.take().get();
            executor.execute(() -> {
                Integer r = null;
                try {
                    r = cs.take().get();
                } catch (Exception e) {}
                save(r);
                m.set(Integer.min(m.get(), r));
                latch.countDown();
            });
            latch.await();
            return m;
        }
        return null;
    }

    private static Integer save(Integer r) {
        return null;
    }

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
