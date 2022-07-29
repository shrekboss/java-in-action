package org.sample;

import org.openjdk.jmh.annotations.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试LinkedList 通过index 方式迭代和foreach 方式迭代的性能差距
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class LinkedListIterationBenchMark {
    private static final int SIZE = 10000;

    private List<String> list = new LinkedList<>();

    @Setup
    public void setUp() {
        for (int i = 0; i < SIZE; i++) {
            list.add(String.valueOf(i));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void forIndexIterate() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
            System.out.print("");
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void forEachIterate() {
        for (String s : list) {
            System.out.print("");
        }
    }
}
