package org.crayzer.err.coding.a.concurrentTools.threadlocal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
@RestController
@RequestMapping("threadlocal")
public class ThreadLocalMisuseController {

    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    /**
     * curl http://localhost:45678/threadlocal/wrong?userId=1
     * curl http://localhost:45678/threadlocal/wrong?userId=2
     * <p/>
     * {"before":"http-nio-8080-exec-1:null","after":"http-nio-8080-exec-1:1"}
     * {"before":"http-nio-8080-exec-1:1","after":"http-nio-8080-exec-1:2"}
     */
    @GetMapping("wrong")
    public Map wrong(@RequestParam("userId") Integer userId) {
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        currentUser.set(userId);
        String after = Thread.currentThread().getName() + ":" + currentUser.get();
        Map result = new HashMap();
        result.put("before", before);
        result.put("after", after);
        return result;
    }

    @GetMapping("right")
    public Map right(@RequestParam("userId") Integer userId) {
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        currentUser.set(userId);
        try {
            String after = Thread.currentThread().getName() + ":" + currentUser.get();
            Map result = new HashMap();
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            //在finally代码块中删除ThreadLocal中的数据，确保数据不串
            currentUser.remove();
        }
    }
}
