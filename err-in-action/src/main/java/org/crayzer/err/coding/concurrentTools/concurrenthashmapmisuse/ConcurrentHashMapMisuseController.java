package org.crayzer.err.coding.concurrentTools.concurrenthashmapmisuse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@RestController
@RequestMapping("concurrenthashmapmisuse")
@Slf4j
public class ConcurrentHashMapMisuseController {

    //线程个数
    private static int THREAD_COUNT = 10;
    //总元素数量
    private static int ITEM_COUNT = 1000;

    // 获得一个指定元素数量模拟数据的ConcurrentHashMap
    private ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.rangeClosed(1, count)
                .boxed()
                .collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(), Function.identity(),
                        (o1, o2) -> o1, ConcurrentHashMap::new));
    }

    /**
     * curl http://localhost:45678/concurrenthashmapmisuse/wrong
     */
    @GetMapping("/wrong")
    public String wrong() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        //初始900个元素
        log.info("init size:{}", concurrentHashMap.size());

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        //使用线程池并发处理逻辑
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            //查询还需要补充多少个元素
            // Question 1: 诸如 size、isEmpty 和 containsValue 等聚合方法，在并发情况下可能会反映
            // ConcurrentHashMap 的中间状态。因此，在并发情况下，这些方法的返回值只能作用于参考，
            // 而不能用于流程控制
            int gap = ITEM_COUNT - concurrentHashMap.size();
            log.info("gap size:{}", gap);
            //补充元素
            // Question 2：putAll 这样的聚合方法也不能确保原子性
            concurrentHashMap.putAll(getData(gap));
        }));
        //等待所有任务完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //最后元素个数会是1000吗？
        log.info("finish size:{}", concurrentHashMap.size());
        return "OK";
    }

    /**
     * curl http://localhost:45678/concurrenthashmapmisuse/right
     */
    @GetMapping("/right")
    public String right() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        log.info("init size:{}", concurrentHashMap.size());

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            //下面的这段复合逻辑需要锁一下这个 ConcurrentHashMap
            synchronized (concurrentHashMap) {
                int gap = ITEM_COUNT - concurrentHashMap.size();
                log.info("gap size:{}", gap);
                concurrentHashMap.putAll(getData(gap));
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);


        log.info("finish size:{}", concurrentHashMap.size());
        return "OK";
    }

    private static final int LOOP_COUNT = 10_000_000;

    @GetMapping("good")
    public String good() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String, Long> normalUse = normalUse();
        stopWatch.stop();
        //校验元素数量
        Assert.isTrue(normalUse.size() == ITEM_COUNT, "normalUse size error");
        //校验累计总数
        Assert.isTrue(normalUse.entrySet().stream()
                        .mapToLong(item -> item.getValue())
                        .reduce(0, Long::sum) == LOOP_COUNT
                , "normalUse count error");
        stopWatch.start("goodUse");
        Map<String, Long> goodUse = goodUse();
        stopWatch.stop();
        Assert.isTrue(goodUse.size() == ITEM_COUNT, "goodUse size error");
        Assert.isTrue(goodUse.entrySet().stream()
                        .mapToLong(item -> item.getValue())
                        .reduce(0, Long::sum) == LOOP_COUNT
                , "goodUse count error");
        log.info(stopWatch.prettyPrint());
        return "OK";
    }

    /**
     * 通过锁的方式锁住 Map，然后做判断、读取现在的累计值、加 1、保存累加后值的逻辑。
     * 这段代码在功能上没有问题，但无法充分发挥 ConcurrentHashMap 的威力
     */
    private Map<String, Long> normalUse() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            synchronized (freqs) {
                if (freqs.containsKey(key)) {
                    freqs.put(key, freqs.get(key) + 1);
                } else {
                    freqs.put(key, 1L);
                }
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }


    private Map<String, Long> goodUse() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    //利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
                    freqs.computeIfAbsent(key, k -> new LongAdder()).increment();

                    // computeIfAbsent和putIfAbsent区别是三点：
                    // 3、当Key不存在的时候，putIfAbsent允许put null进去，而computeIfAbsent不能，之
                    // 后进行containsKey查询是有区别的（当然了，此条针对HashMap，ConcurrentHashMap
                    // 不允许put null value进去）
                    HashMap map = new HashMap();
                    map.put(null, null);
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        return freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );
    }
}
