package org.crayzer.err.coding.collection.linkedlist;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class LinkedListApplication {

    public static void main(String[] args) {
        int elementCount = 100_000;
        int loopCount = 100_000;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("linkedListGet");
        linkedListGet(elementCount, loopCount);
        stopWatch.stop();
        stopWatch.start("arrayListGet");
        arrayListGet(elementCount, loopCount);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start("linkedListAdd");
        linkedListAdd(elementCount, loopCount);
        stopWatch2.stop();
        stopWatch2.start("arrayListAdd");
        arrayListAdd(elementCount, loopCount);
        stopWatch2.stop();
        System.out.println(stopWatch2.prettyPrint());
    }

    // LinkedList访问
    private static void linkedListGet(int elementCount, int loopCount) {
        List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed().collect(Collectors.toCollection(LinkedList::new));
        IntStream.rangeClosed(1, loopCount).forEach(i -> list.get(ThreadLocalRandom.current().nextInt(elementCount)));
    }

    // ArrayList访问
    private static void arrayListGet(int elementCount, int loopCount) {
        List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed().collect(Collectors.toCollection(ArrayList::new));
        IntStream.rangeClosed(1, loopCount).forEach(i -> list.get(ThreadLocalRandom.current().nextInt(elementCount)));
    }

    private static void linkedListAdd(int elementCount, int loopCount) {
        List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed().collect(Collectors.toCollection(LinkedList::new));
        IntStream.rangeClosed(1, loopCount).forEach(i -> list.add(ThreadLocalRandom.current().nextInt(elementCount), 1));
    }

    private static void arrayListAdd(int elementCount, int loopCount) {
        List<Integer> list = IntStream.rangeClosed(1, elementCount).boxed().collect(Collectors.toCollection(ArrayList::new));
        IntStream.rangeClosed(1, loopCount).forEach(i -> list.add(ThreadLocalRandom.current().nextInt(elementCount), 1));
    }

}

