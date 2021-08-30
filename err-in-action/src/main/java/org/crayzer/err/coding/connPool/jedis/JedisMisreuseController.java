package org.crayzer.err.coding.connPool.jedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/jedismisreuse")
@Slf4j
public class JedisMisreuseController {

    private static JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

    @PostConstruct
    public void init() {
        try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {
            Assert.isTrue("OK".equals(jedis.set("a", "1")), "set a = 1 return OK");
            Assert.isTrue("OK".equals(jedis.set("b", "2")), "set b = 2 return OK");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // redis.clients.jedis.Jedis.close
            // 如果 Jedis 是从连接池获取的话，那么 close 方法会调用连接池的 return 方法归还连接
            jedisPool.close();
        }));
    }

    /**
     * 如果多个线程在执行操作，那么既无法确保整条命令以一个原子操作写入 Socket，
     * 也无法确保写入后、读取前没有其他数据写到远端：
     * <p/>
     * redis.clients.jedis.Protocol#sendCommand(redis.clients.jedis.util.RedisOutputStream, byte[], byte[]...)
     * <p/>
     * curl http://localhost:45678/jedismisreuse/wrong
     */
    @GetMapping("/wrong")
    public void wrong() throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String result = jedis.get("a");
                if (!"1".equals(result)) {
                    log.warn("Expect a to be 1 but found {}", result);
                }
            }
            log.info("search 'a' is ok");
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String result = jedis.get("b");
                if (!"2".equals(result)) {
                    log.warn("Expect b to be 2 but found {}", result);
                }
            }
            log.info("search 'b' is ok");
        }).start();
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * curl http://localhost:45678/jedismisreuse/wrong
     */
    @GetMapping("/right")
    public void right() throws InterruptedException {
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                for (int i = 0; i < 1000; i++) {
                    String result = jedis.get("a");
                    if (!result.equals("1")) {
                        log.warn("Expect a to be 1 but found {}", result);
                        return;
                    }
                }
                log.info("search 'b' is ok");
            }
        }).start();

        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                for (int i = 0; i < 1000; i++) {
                    String result = jedis.get("b");
                    if (!result.equals("2")) {
                        log.warn("Expect a to be 1 but found {}", result);
                        return;
                    }
                }
                log.info("search 'a' is ok");
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * curl http://localhost:45678/jedismisreuse/timeout?waittimeout=&conntimeout=
     */
    @GetMapping("timeout")
    public String timeout(@RequestParam("waittimeout") int waittimeout,
                          @RequestParam("conntimeout") int conntimeout) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1);
        config.setMaxWaitMillis(waittimeout);
        try (JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379, conntimeout);
             Jedis jedis = jedisPool.getResource()) {
            return jedis.set("test", "test");
        }
    }
}
