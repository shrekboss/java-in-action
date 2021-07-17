package org.crayzer.jvm.opcode;

import java.util.Random;

public class TestRandomSeed {
    public static void main(String[] args) {
        long seed = 2021;
        Random random = new Random(seed);
        // 1850
        System.out.println(random.nextInt(2021));
        // 1712
        System.out.println(random.nextInt(2021));
    }
}
