package org.crayzer.err.coding.lock.lockgranularity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("lockgranularity")
@Slf4j
public class LockGranularityController {

    private List<Integer> data = new ArrayList<>();

    //不涉及共享资源的慢方法
    private void slow() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
    }

    // wrk -c 2 -d 10s http://localhost:45678/deadlock/wrong
    @GetMapping("wrong")
    public int wrong() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            //加锁粒度太粗了
            synchronized (this) {
                slow();
                data.add(i);
            }
        });
        log.info("took:{}", (System.currentTimeMillis() - begin) / 1000 + "s");
        return data.size();
    }

    // wrk -c 2 -d 10s http://localhost:45678/deadlock/right
    @GetMapping("right")
    public int right() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            slow();
            //只对List加锁
            synchronized (data) {
                data.add(i);
            }
        });
        log.info("took:{}", (System.currentTimeMillis() - begin) / 1000 + "s");
        return data.size();
    }
}
