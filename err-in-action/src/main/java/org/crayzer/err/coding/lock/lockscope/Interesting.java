package org.crayzer.err.coding.lock.lockscope;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@Slf4j
public class Interesting {

    volatile int a = 1;
    volatile int b = 1;

    public static void main(String[] args) {
        Interesting interesting = new Interesting();
        // 两个线程是交错执行 add 和 compare 方法中的业务逻辑，而且这些业务逻辑不是原
        // 子性的：a++ 和 b++ 操作中可以穿插在 compare 方法的比较代码中；更需要注意的
        // 是，a < b 这种操作在字节码层面是 加载 a、加载 b 和比较三步，代码虽然是一行
        // 但也不是原子性的
//        new Thread(interesting::addWrong).start();
//        new Thread(interesting::compareWrong).start();

        new Thread(interesting::addRight).start();
        new Thread(interesting::compareRight).start();
    }

    /**
     * 这个案例中的 add 方法始终只有一个线程在操作，显然只为 add 方法加锁是没用的。
     * public synchronized void add()
     *
     * 正确修改：
     * public synchronized void add()
     * public synchronized void compare()
     * */
    public void addWrong() {
        log.info("add start");
        for (int i = 0; i < 10000; i++) {
            a++;
            b++;
        }
        log.info("add done");
    }

    public synchronized void addRight() {
        log.info("add start");
        for (int i = 0; i < 10000; i++) {
            a++;
            b++;
        }
        log.info("add done");
    }

    // a:185, b:652, true
    public void compareWrong() {
        log.info("compare start");
        for (int i = 0; i < 10000; i++) {
            if (a < b) {
                log.info("a:{}, b:{}, {}", a, b, a > b);
            }
        }
        log.info("compare done");
    }

    public synchronized void compareRight() {
        log.info("compare start");
        for (int i = 0; i < 1000000; i++) {
            Assert.assertTrue(a == b);
            if (a < b) {
                log.info("a:{},b:{},{}", a, b, a > b);
            }
        }
        log.info("compare done");
    }
}
