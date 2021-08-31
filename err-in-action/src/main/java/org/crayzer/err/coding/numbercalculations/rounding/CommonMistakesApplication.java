package org.crayzer.err.coding.numbercalculations.rounding;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {

        System.out.println("wrong1");
        wrong1();
        System.out.println("wrong2");
        wrong2();
        System.out.println("right");
        right();
    }

    private static void wrong1() {

        double num1 = 3.35;
        float num2 = 3.35f;
        // http://www.binaryconvert.com/
        // num1 : Most accurate representation = 3.35000000000000008881784197001E0
        // num2 : Most accurate representation = 3.349999904632568359375E0
        System.out.println(String.format("%.1f", num1));//四舍五入(默认：HALF_UP) 3.4
        System.out.println(String.format("%.1f", num2));// 3.3
    }

    private static void wrong2() {
        double num1 = 3.35;
        float num2 = 3.35f;
        DecimalFormat format = new DecimalFormat("#.##");
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num1)); // 3.35
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num2));// 3.34
    }

    // 浮点数避坑第二原则：浮点数的字符串格式化也要通过 BigDecimal 进行。
    private static void right() {
        double numDouble = 3.35;
        float numFloat = 3.35f;
        BigDecimal numBigDecimal1 = new BigDecimal(Double.toString(numDouble));
        System.out.println(numBigDecimal1.setScale(2, BigDecimal.ROUND_DOWN));
        BigDecimal numFloat2 = new BigDecimal(Float.toString(numFloat));
        System.out.println(numFloat2.setScale(2, BigDecimal.ROUND_HALF_UP));

        BigDecimal num1 = new BigDecimal("3.35");
        BigDecimal num2 = num1.setScale(2, BigDecimal.ROUND_DOWN);
        System.out.println(num2);
        BigDecimal num3 = num1.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(num3);
    }
}

