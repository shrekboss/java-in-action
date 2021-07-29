package org.crayzer.java8.lambda.stream;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalcPoint2D {

    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        double average = calc(ints);
        double streamResult = calcFromLambda(ints);
        //如何用一行代码来实现，比较一下可读性
        assertThat(average, is(streamResult));
    }

    /**
     * describe:
     * - map 方法传入的是一个 Function，可以实现对象转换；
     * - filter 方法传入一个 Predicate，实现对象的布尔判断，只保留返回 true 的数据；
     * - mapToDouble 用于把对象转换为 double；
     * - 通过 average 方法返回一个 OptionalDouble，代表可能包含值也可能不包含值的可空 double。
     **/
    private static double calcFromLambda(List<Integer> ints) {
        return ints.stream()
                .map(i -> new Point2D.Double((double) i % 3, (double) i / 3))
                .filter(point -> point.getY() > 1)
                .mapToDouble(point -> point.distance(0, 0))
                .average()
                .orElse(0);
    }

    private static double calc(List<Integer> ints) {
        List<Point2D> point2DList = new ArrayList<>();
        for (Integer i : ints) {
            point2DList.add(new Point2D.Double((double) i % 3, (double) i / 3));
        }

        double total = 0;
        int count = 0;
        for (Point2D point2D : point2DList) {
            if (point2D.getY() > 1) {
                double distance = point2D.distance(0, 0);
                total += distance;
                count++;
            }
        }
        //注意count可能为0的可能
        return count > 0 ? total / count : 0;
    }
}
