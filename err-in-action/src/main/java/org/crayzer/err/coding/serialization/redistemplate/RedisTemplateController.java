package org.crayzer.err.coding.serialization.redistemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("redistemplate")
@Slf4j
public class RedisTemplateController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String, User> userRedisTemplate;
    @Autowired
    private RedisTemplate<String, Long> countRedisTemplate;


    @PostConstruct
    public void init() throws JsonProcessingException {
        redisTemplate.opsForValue().set("redisTemplate", new User("crayzer", 36));
        stringRedisTemplate.opsForValue().set("stringRedisTemplate", objectMapper.writeValueAsString(new User("crayzer", 36)));
    }

    /**
     * curl http://localhost:45678/redistemplate/wrong
     */
    // - redisTemplate get null
    // - stringRedisTemplate get null
    // redisTemplate & stringRedisTemplate 两者的默认的序列化方式不同
    @GetMapping("wrong")
    public void wrong() {
        log.info("redisTemplate get {}", redisTemplate.opsForValue().get("stringRedisTemplate"));
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get("redisTemplate"));
    }

    /**
     * curl http://localhost:45678/redistemplate/right
     */
    // - redisTemplate get User(name=crayzer, age=36)
    // - stringRedisTemplate get User(name=crayzer, age=36)
    @GetMapping("right")
    public void right() throws JsonProcessingException {
        //使用RedisTemplate获取Value，无需反序列化就可以拿到实际对象，虽然方便，但是Redis中保存的Key和Value不易读
        User userFromRedisTemplate = (User) redisTemplate.opsForValue().get("redisTemplate");
        log.info("redisTemplate get {}", userFromRedisTemplate);
        // 使用StringRedisTemplate，虽然Key正常，但是Value存取需要手动序列化成字符串
        User userFromStringRedisTemplate = objectMapper.readValue(stringRedisTemplate.opsForValue().get("stringRedisTemplate"), User.class);
        log.info("stringRedisTemplate get {}", userFromStringRedisTemplate);
    }

    /**
     * curl http://localhost:45678/redistemplate/right2
     */
    // [14:07:41.315] [http-nio-45678-exec-1] [INFO ] [.t.c.s.demo1.RedisTemplateController:55  ] - userRedisTemplate get {name=crayzer, age=36} class java.util.LinkedHashMap
    // [14:07:41.318] [http-nio-45678-exec-1] [INFO ] [.t.c.s.demo1.RedisTemplateController:56  ] - stringRedisTemplate get {"name":"crayzer","age":36}
    // redis-cli: get crayzer
    @GetMapping("right2")
    public void right2() {
        User user = new User("crayzer", 36);
        userRedisTemplate.opsForValue().set(user.getName(), user);
        Object userFromRedis = userRedisTemplate.opsForValue().get(user.getName());
        // LinkedHashMap cannot be cast to User
        // User userFromRedis = userRedisTemplate.opsForValue().get(user.getName());
        log.info("userRedisTemplate get {} {}", userFromRedis, userFromRedis.getClass());
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get(user.getName()));
    }

    /**
     * curl http://localhost:45678/redistemplate/right3
     */
// - userRedisTemplate get User(name=crayzer, age=36) class org.crayzer.err.coding.serialization.redistemplate.User
// - stringRedisTemplate get {"@class":"org.crayzer.err.coding.serialization.redistemplate.User","name":"crayzer","age":36}
// redis-cli: get crayzer
    @GetMapping("right3")
    public void right3() {
        User user = new User("crayzer", 36);
        userRedisTemplate.opsForValue().set(user.getName(), user);
        User userFromRedis = userRedisTemplate.opsForValue().get(user.getName());
        log.info("userRedisTemplate get {} {}", userFromRedis, userFromRedis.getClass());
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get(user.getName()));
    }

    /**
     * curl http://localhost:45678/redistemplate/wrong2
     */
    @GetMapping("wrong2")
    public void wrong2() {
        String key = "testCounter";
        countRedisTemplate.opsForValue().set(key, 1L);
        log.info("{} {}", countRedisTemplate.opsForValue().get(key), countRedisTemplate.opsForValue().get(key) instanceof Long);
        Long l1 = getLongFromRedis(key);
        countRedisTemplate.opsForValue().set(key, Integer.MAX_VALUE + 1L);
        log.info("{} {}", countRedisTemplate.opsForValue().get(key), countRedisTemplate.opsForValue().get(key) instanceof Long);
        Long l2 = getLongFromRedis(key);
        log.info("{} {}", l1, l2);
    }

    private Long getLongFromRedis(String key) {
        Object o = countRedisTemplate.opsForValue().get(key);
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }
        if (o instanceof Long) {
            return (Long) o;
        }
        return null;
    }
}
