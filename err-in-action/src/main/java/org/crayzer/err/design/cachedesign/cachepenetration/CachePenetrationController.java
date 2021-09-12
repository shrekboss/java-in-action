package org.crayzer.err.design.cachedesign.cachepenetration;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RequestMapping("cachepenetration")
@RestController
public class CachePenetrationController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BloomFilter<Integer> bloomFilter;

    @PostConstruct
    public void init() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);

        //创建布隆过滤器，元素数量10000，期望误判率1%
        bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 10000, 0.01);
        //填充布隆过滤器
        IntStream.rangeClosed(1, 10000).forEach(bloomFilter::put);
    }

    @GetMapping("wrong")
    public String wrong(@RequestParam("id") int id) {
        String key = "user" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        //无法区分是无效用户还是缓存失效
        if (StringUtils.isEmpty(data)) {
            data = getCityFromDb(id);
            stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
        }
        return data;
    }


    @GetMapping("right")
    public String right(@RequestParam("id") int id) {
        String key = "user" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(data)) {
            data = getCityFromDb(id);
            //校验从数据库返回的数据是否有效
            if (!StringUtils.isEmpty(data)) {
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
            }
            else {
                //如果无效，直接在缓存中设置一个NODATA，这样下次查询时即使是无效用户还是可以命中缓存
                stringRedisTemplate.opsForValue().set(key, "NODATA", 30, TimeUnit.SECONDS);
            }
        }
        return data;
    }

    @GetMapping("right2")
    public String right2(@RequestParam("id") int id) {
        String data = "";
        //通过布隆过滤器先判断
        if (bloomFilter.mightContain(id)) {
            String key = "user" + id;
            //走缓存查询
            data = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(data)) {
                //走数据库查询
                data = getCityFromDb(id);
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
            }
        }
        return data;
    }

    private String getCityFromDb(int id) {
        atomicInteger.incrementAndGet();
        if (id > 0 && id <= 10000) return "userdata";
        return "";
    }
}
