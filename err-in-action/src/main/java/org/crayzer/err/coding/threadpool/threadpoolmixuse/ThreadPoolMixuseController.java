package org.crayzer.err.coding.threadpool.threadpoolmixuse;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static org.crayzer.err.coding.threadpool.PrintThreadPoolStats.printStats;

@RestController
@RequestMapping("threadpoolmixuse")
@Slf4j
public class ThreadPoolMixuseController {

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2, 2,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("batchfileprocess-threadpool-%d").get(),
            // 因为开启了 CallerRunsPolicy 拒绝处理策略，所以当线程满载队列也满的情况下，任务会在提交任
            // 务的线程，或者说调用 execute 方法的线程执行，也就是说不能认为提交到线程池的任务就一定
            // 是异步处理的。如果使用了 CallerRunsPolicy 策略，那么有可能异步任务变为同步执行。这个
            // 拒绝策略比较特别的原因。
            new ThreadPoolExecutor.CallerRunsPolicy());

    private static ThreadPoolExecutor asyncCalcThreadPool = new ThreadPoolExecutor(
            200, 200,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("asynccalc-threadpool-%d").get());

    /**
     * describe: 模拟一下文件批处理的代码，在程序启动后通过一个线程开启死循环逻辑，不断向线程池提交任务，
     * 任务的逻辑是向一个文件中写入大量的数据：
     **/
    // @PostConstruct
    public void init() {
        printStats(threadPool);

        new Thread(() -> {
            String payload = IntStream.rangeClosed(1, 1_000_000)
                    .mapToObj(__ -> "a")
                    .collect(Collectors.joining(""));
            while (true) {
                threadPool.execute(() -> {
                    try {
                        Files.write(
                                Paths.get("demo.txt"),
                                Collections.singletonList(LocalTime.now().toString() + ":" + payload),
                                UTF_8, CREATE, TRUNCATE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    log.info("batch file processing done");
                });
            }
        }).start();
    }

    /**
     * wrk -t100 -c100 -d 10s http://localhost:8080/threadpoolmixuse/wrong
     * tps: 74.99
     * <p>
     * 因为原来执行 IO 任务的线程池使用的是 CallerRunsPolicy 策略，所以直接使用这个线程池进行异步
     * 计算的话，当线程池饱和的时候，计算任务会在执行 Web 请求的 Tomcat 线程执行，这时就会进一步
     * 影响到其他同步处理的线程，甚至造成整个应用程序崩溃。
     */
    @GetMapping("wrong")
    public int wrong() throws ExecutionException, InterruptedException {
        return threadPool.submit(calcTask()).get();
    }

    /**
     * wrk -t10 -c100 -d 10s http://localhost:8080/threadpoolmixuse/right
     * tps: 1727.68
     */
    @GetMapping("right")
    public int right() throws ExecutionException, InterruptedException {
        return asyncCalcThreadPool.submit(calcTask()).get();
    }

    private Callable<Integer> calcTask() {
        return () -> {
            TimeUnit.MILLISECONDS.sleep(10);
            return 1;
        };
    }
}
