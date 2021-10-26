package org.crayzer.conc.juc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * <p>一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * <p>二、线程池的体系结构：
 * java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * <li>  |--ExecutorService 子接口: 线程池的主要接口
 * <li>   |--ThreadPoolExecutor 线程池的实现类
 * <li>   |--ScheduledExecutorService 子接口：负责线程的调度
 * <li>    |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * <p>三、工具类 : Executors
 * <li>ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * <li>ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * <li>ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * <li>ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestThreadPool {

    @Test
    public void threadPoolTest() throws ExecutionException, InterruptedException {
        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        //submit Callable方法
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int i = 0; i <= 100; i++) {
                        sum += i;
                    }
                    return sum;
                }
            });
            list.add(future);
        }
        pool.shutdown();
        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }

        //submit Runnable方法
        ThreadPoolDemo tpd = new ThreadPoolDemo();
        //2. 为线程池中的线程分配任务
        for (int i = 0; i < 10; i++) {
            pool.submit(tpd);
        }
        //3. 关闭线程池
        pool.shutdown();
    }

    @Test
    public void ScheduledThreadPoolTest() throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Future<Integer> result = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);//生成随机数
                    System.out.println(Thread.currentThread().getName() + " : " + num);
                    return num;
                }
            }, 1, TimeUnit.SECONDS);
            System.out.println(result.get());
        }
        pool.shutdown();
    }

}

class ThreadPoolDemo implements Runnable {
    private int i = 0;

    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }
}
