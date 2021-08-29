package org.crayzer.err.coding.lock.lockscope;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("lockscope")
@Slf4j
public class LockScopeController {

    /**
     * curl http://localhost:45678/lockscope/wrong
     */
    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        WrongData.reset();
        //多线程循环一定次数调用Data类不同实例的wrong方法
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new WrongData().wrong());
        return WrongData.getCounter();
    }

    /**
     * curl http://localhost:45678/lockscope/right
     */
    @GetMapping("right")
    public int right(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        RightData.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new RightData().right());
        return RightData.getCounter();
    }

    /**
     * curl http://localhost:45678/lockscope/wrong2
     */
    @GetMapping("wrong2")
    public String wrong2() {
        Interesting interesting = new Interesting();
        new Thread(interesting::addRight).start();
        new Thread(interesting::compareWrong).start();
        return "OK";
    }

    /**
     * curl http://localhost:45678/lockscope/right2
     */
    @GetMapping("right2")
    public String right2() {
        Interesting interesting = new Interesting();
        new Thread(interesting::addRight).start();
        new Thread(interesting::compareRight).start();
        return "OK";
    }

    // 897415623
    // 123456789
    // 36810794125
    public static void main(String[] args) {
        IntStream.range(1, 10).parallel().forEach(i -> System.out.print(i + " "));
        System.out.println();
        IntStream.range(1, 10).parallel().forEachOrdered(i -> System.out.print(i + " "));
        System.out.println();
        IntStream.rangeClosed(1, 10).parallel().forEach(i -> System.out.print(i + " "));
    }
}
