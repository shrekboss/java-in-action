package org.crayzer.java8.lambda.stream.parallel;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class parallelDemo {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).parallel().forEach(i -> {
            System.out.println(LocalDateTime.now() + " : " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        });
    }
}
