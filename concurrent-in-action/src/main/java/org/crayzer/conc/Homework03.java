package org.crayzer.conc;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

 //       int result = sum(); //这是得到的返回值

        // v1
//        Future<Integer> result = singleThreadPool.submit(new Task()); // 114 ms
        // v2
//        FutureTask<Integer> result = new FutureTask<>(new Task());
//        singleThreadPool.execute(result); // 103 ms
        // v3
        CompletableFuture<Integer> result = getResult(); // 205 ms


        // 确保  拿到result 并输出
        // System.out.println("异步计算结果为："+ result); // 24157817

        System.out.println("异步计算结果为："+ result.get());

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
        // 关闭线程池
        if(!singleThreadPool.isShutdown()){
            singleThreadPool.shutdown();
        }
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            return sum();
        }
    }

    private static CompletableFuture<Integer> getResult() {
        return CompletableFuture.supplyAsync(() -> sum());
    }
}
