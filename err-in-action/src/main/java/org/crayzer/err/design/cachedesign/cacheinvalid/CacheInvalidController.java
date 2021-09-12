package org.crayzer.err.design.cachedesign.cacheinvalid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RequestMapping("cacheinvalid")
@RestController
public class CacheInvalidController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private AtomicInteger atomicInteger = new AtomicInteger();

    // @PostConstruct
    public void wrongInit() {
        IntStream.rangeClosed(1, 1000).forEach(i -> stringRedisTemplate.opsForValue().set("city" + i, getCityFromDb(i), 30, TimeUnit.SECONDS));
        log.info("Cache init finished");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

//    @PostConstruct
    public void rightInit1() {
        // 设置缓存的过期时间是 30 秒 + 10 秒以内的随机延迟（扰动值）
        IntStream.rangeClosed(1, 1000).forEach(i -> stringRedisTemplate.opsForValue().set("city" + i, getCityFromDb(i), 30 + ThreadLocalRandom.current().nextInt(10), TimeUnit.SECONDS));
        log.info("Cache init finished");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

     @PostConstruct
    public void rightInit2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 让缓存不主动过期
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            IntStream.rangeClosed(1, 1000).forEach(i -> {
                String data = getCityFromDb(i);
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                }
                if (!StringUtils.isEmpty(data)) {
                    stringRedisTemplate.opsForValue().set("city" + i, data);
                }
            });
            log.info("Cache update finished");
            countDownLatch.countDown();
        }, 0, 30, TimeUnit.SECONDS);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);

        countDownLatch.await();
    }

    /**
     * wrk -c10 -t10 -d 100s http://localhost:45678/cacheinvalid/city
     */
    @GetMapping("city")
    public String city() {
        int id = ThreadLocalRandom.current().nextInt(1000) + 1;
        String key = "city" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (data == null) {
            data = getCityFromDb(id);
            if (!StringUtils.isEmpty(data))
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
        }
        return data;
    }


    private String getCityFromDb(int cityId) {
        atomicInteger.incrementAndGet();
        return "citydata" + System.currentTimeMillis();
    }
}
