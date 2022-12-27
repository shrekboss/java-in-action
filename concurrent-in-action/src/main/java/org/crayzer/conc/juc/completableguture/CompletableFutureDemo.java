package org.crayzer.conc.juc.completableguture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * 利用 CompletableFuture 中的一些 API
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        ///////////////////////////////////////////////////
        // 任务一执行流程
        ///////////////////////////////////////////////////
        // 执行 taskA1
        CompletableFuture<Integer> taskA1 = CompletableFuture.supplyAsync(() -> 1);
        // taskA1 执行完后，再并发执行 taskB1、taskC1
        CompletableFuture<Integer> taskB1 = taskA1.thenApplyAsync(integer -> 2);
        CompletableFuture<Integer> taskC1 = taskA1.thenApplyAsync(integer -> 3);
        // 任务一的结果
        CompletableFuture<Integer> result1 =
                // 等到 taskB2、taskC2 都执行完并合并结果后
                taskB1.thenCombine(taskC1, Integer::sum)
                        // 再合并 taskA1 的结果后
                        .thenCombine(taskA1, Integer::sum)
                        // 再异步执行 taskD1
                        .thenApplyAsync(integer -> integer + 4);

        ///////////////////////////////////////////////////
        // 任务二执行流程
        ///////////////////////////////////////////////////
        // 执行 taskA2
        CompletableFuture<Integer> taskA2 = CompletableFuture.supplyAsync(() -> 1);
        // taskA2 执行完后，再并发执行 taskB2、taskC2
        CompletableFuture<Integer> taskB2 = taskA2.thenApplyAsync(integer -> 2);
        CompletableFuture<Integer> taskC2 = taskA2.thenApplyAsync(integer -> 3);
        // 任务二的结果
        CompletableFuture<Integer> result2 =
                // 等到 taskB2、taskC2 任意其中一个有结果后
                taskB2.applyToEither(taskC2, Function.identity())
                        // 再合并 taskA2 的结果后
                        .thenCombine(taskA2, Integer::sum)
                        // 再异步执行 taskD2
                        .thenApplyAsync(integer -> integer + 4);

        ///////////////////////////////////////////////////
        // 任务一 + 任务二，合并结果
        ///////////////////////////////////////////////////
        CompletableFuture<Integer> result = result1.thenCombine(result2, Integer::sum);
        try {
            // 任务总超时时间设置为5s
            System.out.println(result.get(5, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // 超时则打印0
            System.out.println(0);
            e.printStackTrace();
        }
    }
}
