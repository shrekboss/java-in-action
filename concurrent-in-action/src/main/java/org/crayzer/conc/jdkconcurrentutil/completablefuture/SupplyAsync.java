package org.crayzer.conc.jdkconcurrentutil.completablefuture;

import java.util.concurrent.CompletableFuture;

public class SupplyAsync {

    /*这是一个异步流程，但任务①②③却是串行执行的，②依赖①的执行结果，③依赖②的执行结果*/
    public static void main(String[] args) {
        CompletableFuture<String> f0 =
                CompletableFuture.supplyAsync(() -> "Hello world") // ①
                .thenApply(s -> s + "QQ")       // ②
                .thenApply(String::toUpperCase); // ③

        System.out.println(f0.join());
    }
}
