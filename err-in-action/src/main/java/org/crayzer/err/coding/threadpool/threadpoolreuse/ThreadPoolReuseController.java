package org.crayzer.err.coding.threadpool.threadpoolreuse;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("threadpoolreuse")
@Slf4j
public class ThreadPoolReuseController {

    @GetMapping("wrong")
    public String wrong() throws InterruptedException {
        ThreadPoolExecutor threadPool = ThreadPoolHelper.getThreadPool();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            threadPool.execute(() -> {
                String payload = IntStream.rangeClosed(1, 1000000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining("")) + UUID.randomUUID().toString();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                log.debug(payload);
            });
        });
        return "OK";
    }

    static class ThreadPoolHelper {
        private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10, 50,
                2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").get());

        public static ThreadPoolExecutor getThreadPool() {
            /**
             * 线程池没有复用
             *
             * 可以想到 newCachedThreadPool 会在需要时创建必要多的线程，业务代码的一次业务操作会向
             * 线程池提交多个慢任务，这样执行一次业务操作就会开启多个线程。如果业务操作并发量较大的话，
             * 的确有可能一下子开启几千个线程。
             */
            return (ThreadPoolExecutor) Executors.newCachedThreadPool();
        }

        /**
         * 修复这个 Bug 也很简单，使用一个静态字段来存放线程池的引用，返回线程池的代码直接返回这个静态字段即可。
         */
        public static ThreadPoolExecutor getRightThreadPool() {
            return threadPoolExecutor;
        }
    }
}
