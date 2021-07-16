package org.crayzer.conc.jdkconcurrentutil.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author yizhe.chen
 */
public class SumTask extends RecursiveTask<Long> {

    static final int THRESHOLD = 100;
    long[] array;
    int start;
    int end;

    public SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start < THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("compute %d~%d = %d%n", start, end, sum);
            return sum;
        }

        int middle = (start + end) / 2;
        System.out.printf("split %d~%d ==> %d~%d, %d~%d%n", start, end, start, middle, middle, end);
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);

        /*
         * 错误写法:
         *      分别对子任务调用 fork()
         *
         * 因为执行compute()方法的线程本身也是一个Worker线程，当对两个子任务调用fork()时，
         * 这个Worker线程就会把任务分配给另外两个Worker，但是它自己却停下来等待不干活了！
         */
        // subtask1.fork();
        // subtask2.fork();
        invokeAll(subtask1, subtask2);

        Long subResult1 = subtask1.join();
        Long subResult2 = subtask2.join();
        Long result = subResult1 + subResult2;
        System.out.println("result = " + subResult1 + " + " + subResult2);
        return result;
    }

    public static void main(String[] args) throws Exception {
        // 创建随机数组成的数组:
        long[] array = new long[400];
        fillRandom(array);

        /*
         * fork/join task:
         *      JDK用来执行Fork/Join任务的工作线程池大小等于CPU核心数
         */
        ForkJoinPool fjp = new ForkJoinPool(4);
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = fjp.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    private static void fillRandom(long[] array) {
        Random random = new Random();
        long temp;
        for (int i = 0; i < array.length; i++) {
            temp = random.nextLong();
            System.out.println("temp = " + temp);
            array[i] = temp;
        }
    }
}
