package org.crayzer.conc.juc;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <p>Callable 接口类似于 Runnable，两者都是为那些其实例可能被另一个线程执行的类设计的。但是 Runnable 不会返回结果，并且无法抛出经过检查的异常。
 * <p>Callable 需要依赖FutureTask ， FutureTask 也可以用作闭锁。
 *
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 * @since 1.0.0
 */
public class TestCallable {

    @Test
    public void callableTest() throws ExecutionException, InterruptedException {
        CallableThreadDemo td = new CallableThreadDemo();
        //futureTask 实现类的支持，用于接收运算结果
        FutureTask<Integer> result = new FutureTask<>(td);
        new Thread(result).start();
        //线程开始运行
        Integer sum = result.get();
        //等待线程执行完成后，才能获取到结果 也可以用于闭锁操作作为等待项
        System.out.println("总和" + sum);
    }
}

/**
 * 多了一个方法的返回值  并且可以抛出异常
 */
class CallableThreadDemo implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}

//class ThreadDemo implements Runnable {
//    @Override
//    public void run() {
//    }
//}

