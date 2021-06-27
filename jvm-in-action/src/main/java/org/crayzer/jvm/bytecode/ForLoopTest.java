package org.crayzer.jvm.bytecode;

/**
 * javap -c -verbose jvm-in-action/target/classes/org/crayzer/jvm/bytecode/ForLoopTest
 */
public class ForLoopTest {
    private static final int[] numsbers = {1, 6, 8};

    public static void main(String[] args) {
        MovingAverage ma = new MovingAverage();
        for (int number : numsbers) {
            ma.submit(number);
        }
        double avg = ma.getAvg();
    }
}
