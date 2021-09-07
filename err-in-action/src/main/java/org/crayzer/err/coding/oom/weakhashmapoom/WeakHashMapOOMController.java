package org.crayzer.err.coding.oom.weakhashmapoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

@RestController
@RequestMapping("weakhashmapoom")
@Slf4j
public class WeakHashMapOOMController {
    private Map<User, UserProfile> cache = new WeakHashMap<>();
    // Value 变为弱引用，使用 WeakReference 来包装 UserProfile
    private Map<User, WeakReference<UserProfile>> cache2 = new WeakHashMap<>();
    private Map<User, UserProfile> cache3 = new ConcurrentReferenceHashMap<>();

    /**
     * WeakHashMap 的 Key 虽然是弱引用，但是其 Value 却持有 Key 中对象的强引用，Value 被
     * Entry 引用，Entry 被 WeakHashMap 引用，最终导致 Key 无法回收
     * <p/>
     * curl http://localhost:45678/weakhashmapoom/wrong
     */
    @GetMapping("wrong")
    public void wrong() {
        String userName = "crayzer";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", cache.size()), 1, 1, TimeUnit.SECONDS);
        LongStream.rangeClosed(1, 2000000).forEach(i -> {
            User user = new User(userName + i);
            cache.put(user, new UserProfile(user, "location" + i));
        });
    }

    /**
     * curl http://localhost:45678/weakhashmapoom/right
     */
    @GetMapping("right")
    public void right() {
        String userName = "crayzer";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", cache2.size()), 1, 1, TimeUnit.SECONDS);
        LongStream.rangeClosed(1, 2000000).forEach(i -> {
            User user = new User(userName + i);
            // 使用弱引用来包装UserProfile
            cache2.put(user, new WeakReference(new UserProfile(user, "location" + i)));
        });
    }

    /**
     * 让 Value 也就是 UserProfile 不再引用 Key，而是重新 new 出一个新的 User 对象赋值给 UserProfile
     * <p/>
     * curl http://localhost:45678/weakhashmapoom/right2
     */
    @GetMapping("right2")
    public void right2() {
        String userName = "crayzer";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", cache.size()), 1, 1, TimeUnit.SECONDS);
        LongStream.rangeClosed(1, 2000000).forEach(i -> {
            User user = new User(userName + i);
            // 让 Value 也就是 UserProfile 不再引用 Key
            cache.put(user, new UserProfile(new User(user.getName()), "location" + i));
        });
    }

    @GetMapping("right3")
    public void right3() {
        String userName = "crayzer";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", cache3.size()), 1, 1, TimeUnit.SECONDS);
        LongStream.rangeClosed(1, 20000000).forEach(i -> {
            User user = new User(userName + i);
            cache3.put(user, new UserProfile(user, "location" + i));
        });
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class User {
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserProfile {
        private User user;
        private String location;
    }
}
