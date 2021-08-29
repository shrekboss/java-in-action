package org.crayzer.err.coding.concurrentTools.copyonwritelistmisuse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("copyonwritelistmisuse")
@Slf4j
public class CopyOnWriteListMisuseController {

    /**
     * curl http://localhost:45678/copyonwritelistmisuse/write
     */
    //测试并发写的性能
    @GetMapping("/write")
    public Map<String, Integer> testWrite() {
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        StopWatch stopWatch = new StopWatch();
        int loopCount = 100_000;
        stopWatch.start("Write:copyOnWriteArrayList");
        //循环100000次并发往CopyOnWriteArrayList写入随机元素
        IntStream.rangeClosed(1, loopCount).parallel().forEach(
                i -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        stopWatch.start("Write:synchronizedList");
        //循环100000次并发往加锁的ArrayList写入随机元素
        IntStream.rangeClosed(1, loopCount).parallel().forEach(
                __ -> synchronizedList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        Map<String, Integer> result = new HashMap<>();
        result.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        return result;
    }

    //帮助方法用来填充List
    private void addAll(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1, 1000_000).boxed().collect(Collectors.toList()));
    }

    /**
     * curl http://localhost:45678/copyonwritelistmisuse/read
     */
    //测试并发读的性能
    @GetMapping("/read")
    public Map<String, Integer> testRead() {
        //创建两个测试对象
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        //填充数据
        addAll(copyOnWriteArrayList);
        addAll(synchronizedList);
        StopWatch stopWatch = new StopWatch();
        int loopCount = 1000000;
        int count = copyOnWriteArrayList.size();
        stopWatch.start("Read:copyOnWriteArrayList");
        //循环1000000次并发从CopyOnWriteArrayList随机查询元素
        IntStream.rangeClosed(1, loopCount).parallel().forEach(
                __ -> copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(count)));
        stopWatch.stop();
        stopWatch.start("Read:synchronizedList");
        //循环1000000次并发从加锁的ArrayList随机查询元素
        IntStream.range(0, loopCount).parallel().forEach(
                __ -> synchronizedList.get(ThreadLocalRandom.current().nextInt(count)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        Map<String, Integer> result = new HashMap<>();
        result.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        return result;
    }
}
