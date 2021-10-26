package org.crayzer.conc.juc.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SimpleDateFormat。（当多个线程共用这样一个SimpleDateFormat，但是这个类是不安全的）
 * <p>
 * 2个线程分别用自己的SimpleDateFormat，这没问题；
 * <p/>
 * 后来延伸出10个，那就有10个线程和10个SimpleDateFormat，这虽然写法不优雅，但勉强可以接受
 * 但是当需求变成了1000，那么必然要用线程池，消耗内存太多；
 * <p/>
 * 但是每一个SimpleDateFormat我们都需要创建一遍，那么太耗费new对象了，改成static共用的，
 * 所有线程都共用一个simpleDateFormat对象，但这是线程不安全的，容易出现时间一致的情况，
 * 在调用的时候，可加锁来解决，但还是不优雅；
 * <p/>
 * 用ThreadLocal来解决该问题，给每个线程分配一个simpledateformat，可这个threadlocal是安全的；
 */
public class ThreadLocalForSimpleDateFormat {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                String date = new ThreadLocalForSimpleDateFormat().date(finalI);
                System.out.println(finalI + " => " + date);
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
//        Date date = new Date(1000 * seconds);
        Date date = new Date();
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal2.get();
        return dateFormat.format(date);
    }
}

class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S"));
}

