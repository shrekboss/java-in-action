package org.crayzer.err.design.cachedesign.cacheconcurrent;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequestMapping("cacheconcurrent")
@RestController
public class CacheConcurrentController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private AtomicInteger atomicInteger = new AtomicInteger();
    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        //初始化一个热点数据到Redis中，过期时间设置为5秒
        stringRedisTemplate.opsForValue().set("hotspot", getExpensiveData(), 5, TimeUnit.SECONDS);
        //每隔1秒输出一下回源的QPS
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

    @GetMapping("wrong")
    public String wrong() {
        String data = stringRedisTemplate.opsForValue().get("hotspot");
        if (StringUtils.isEmpty(data)) {
            data = getExpensiveData();
            //重新加入缓存，过期时间还是5秒
            stringRedisTemplate.opsForValue().set("hotspot", data, 5, TimeUnit.SECONDS);
        }
        return data;
    }


    @GetMapping("right")
    public String right() {
        String data = stringRedisTemplate.opsForValue().get("hotspot");
        if (StringUtils.isEmpty(data)) {
            RLock locker = redissonClient.getLock("locker");
            //获取分布式锁
            if (locker.tryLock()) {
                try {
                    data = stringRedisTemplate.opsForValue().get("hotspot");
                    //双重检查，因为可能已经有一个B线程过了第一次判断，在等锁，然后A线程已经把数据写入了Redis中
                    if (StringUtils.isEmpty(data)) {
                        //回源到数据库查询
                        data = getExpensiveData();
                        stringRedisTemplate.opsForValue().set("hotspot", data, 5, TimeUnit.SECONDS);
                    }
                } finally {
                    //别忘记释放，另外注意写法，获取锁后整段代码try+finally，确保unlock万无一失
                    locker.unlock();
                }
            }
        }
        return data;
    }

    private String getExpensiveData() {
        atomicInteger.incrementAndGet();
        return "important data";
    }
}
