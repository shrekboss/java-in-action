package org.crayzer.err.coding.a.concurrentTools.threadlocal;

import groovy.util.logging.Slf4j;
import jline.internal.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
@RestController
@RequestMapping("threadlocalmemoryleak")
@Slf4j
public class ThreadLocalMemoryLeakController {

    private static final ThreadLocal<List<String>> data = ThreadLocal.withInitial(() -> null);

    @GetMapping("wrong")
    public void wrong() {
        List<String> d = IntStream.rangeClosed(1, 10).mapToObj(i -> IntStream.rangeClosed(1, 1000000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID())
                .collect(Collectors.toList());
        data.set(d);

        Log.info("data:[{}]", data);
    }
}
