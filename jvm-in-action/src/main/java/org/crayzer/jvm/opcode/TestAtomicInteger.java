package org.crayzer.jvm.opcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger {

    public static void main(String... args) {
        Map<String, AtomicInteger> counterMap = new HashMap<>();
        for (String arg : args) {
            AtomicInteger counter = counterMap.get(arg);
            if (Objects.isNull(counter)) {
                counter = new AtomicInteger();
                counterMap.put(arg, counter);
            }
            // 作为可变数字包装类; 到处传递
            counter.incrementAndGet();
        }
    }
}
