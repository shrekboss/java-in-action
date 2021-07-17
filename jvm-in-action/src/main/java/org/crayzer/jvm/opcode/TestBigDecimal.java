package org.crayzer.jvm.opcode;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestBigDecimal {

    public static void main(String... args) {
        BigDecimal num1 = new BigDecimal("1.68168");
        System.out.println(num1.compareTo(BigDecimal.ZERO));
        BigDecimal result1 = num1.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println(result1.toPlainString());
        // java.lang.ArithmeticException: Rounding necessary
        BigDecimal result2 = num1.setScale(2);
        System.out.println(result2.toPlainString());

        BigDecimal num2 = new BigDecimal("2.666");
        BigDecimal divideResult1 = num1.divide(num2, RoundingMode.HALF_UP);
        System.out.println(divideResult1.toPlainString());
        // java.lang.ArithmeticException: Non-terminating decimal expansion;
        // no exact representable decimal result.
        BigDecimal divideResult = num1.divide(num2);
        System.out.println(divideResult.toPlainString());
    }
}
