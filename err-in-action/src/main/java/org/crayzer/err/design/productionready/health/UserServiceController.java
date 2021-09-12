package org.crayzer.err.design.productionready.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("user")
public class UserServiceController {

    /**
     * http://localhost:45679/admin/health
     * curl http://localhost:45678/user?userId=1
     */
    @GetMapping
    public User getUser(@RequestParam("userId") long id) {
        //一半概率返回正确响应，一半概率抛异常
        if (ThreadLocalRandom.current().nextInt() % 2 == 0)
            return new User(id, "name" + id);
        else
            throw new RuntimeException("error");
    }

    /**
     * http://localhost:45679/admin/health
     * curl http://localhost:45678/user/slowTask
     */
    @GetMapping("slowTask")
    public void slowTask() {
        ThreadPoolProvider.getDemoThreadPool().execute(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
            }
        });
    }
}
