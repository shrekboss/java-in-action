package org.crayzer.err.coding.numbercalculations.overflowissue;

import java.math.BigInteger;

public class CommonMistakesApplication {

    public static void main(String[] args) {

        System.out.println("wrong");
        wrong();
        System.out.println("right1");
        right1();
        System.out.println("right2");
        right2();
    }

    // Long 的最大值 +1 变为了 Long 的最小值
    private static void wrong() {
        long l = Long.MAX_VALUE;
        System.out.println(l + 1);
        System.out.println(l + 1 == Long.MIN_VALUE);
    }

    // 考虑使用 Math 类的 addExact、subtractExact 等 xxExact 方法进行数值运算，这些方法可以在数值
    // 溢出时主动抛出 ArithmeticException 异常
    private static void right2() {
        try {
            long l = Long.MAX_VALUE;
            System.out.println(Math.addExact(l, 1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void right1() {
        BigInteger i = new BigInteger(String.valueOf(Long.MAX_VALUE));
        System.out.println(i); // 9223372036854775807
        System.out.println(i.add(BigInteger.ONE).toString()); // 9223372036854775808

        try {
            // 转换出现溢出时，同样会抛出 ArithmeticException
            long l = i.add(BigInteger.ONE).longValueExact();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

