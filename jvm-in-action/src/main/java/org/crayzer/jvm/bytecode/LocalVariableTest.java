package org.crayzer.jvm.bytecode;

/**
 * javac -g HelloByteCode.java
 * <p/>
 * javap -c -verbose org.crayzer.jvm.bytecode.LocalVariableTest
 */
public class LocalVariableTest {

    public static void main(String[] args) {
        MovingAverage ma = new MovingAverage();
        int num1 = 1;
        int num2 = 2;
        ma.submit(num1);
        ma.submit(num2);
        double avg = ma.getAvg();
        System.out.println(avg);
    }
}
