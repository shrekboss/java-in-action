package org.crayzer.conc.juc.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class QueryCoderByForking {

    public static Integer inquirey(String[] args) {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        // 用于保存Future对象
        List<Future<Integer>> futures = new ArrayList<>(3);
        //提交异步任务，并保存future到futures
        futures.add(cs.submit(() -> geocoderByS1()));
        futures.add(cs.submit(() -> geocoderByS2()));
        futures.add(cs.submit(() -> geocoderByS3()));
        // 获取最快返回的任务执行结果
        Integer r = 0;
        try {
            // 只要有一个成功返回，则break
            for (int i = 0; i < 3; ++i) {
                r = cs.take().get();
                //简单地通过判空来检查是否成功返回
                if (r != null) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            for (Future<Integer> f : futures)
                f.cancel(true);
        }
        return r;
    }

    private static Integer save(Integer r) {
        return null;
    }

    private static Integer geocoderByS1() {
        return null;
    }

    private static Integer geocoderByS2() {
        return null;
    }

    private static Integer geocoderByS3() {
        return null;
    }
}
