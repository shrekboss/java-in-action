package org.crayzer.java8.lambda.stream.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * @author yizhe.chen
 */
public class StreamApiDemo {

    public static void main(String[] args) throws IOException {
        generatorStream();
        doForeach(null);
    }

    /**
     * @author yixiu
     * <p>
     * 筛选出卡路里小于400的菜肴
     * 对筛选出的菜肴进行一个排序
     * 获取排序后菜肴的名字
     **/
    private List<String> afterJava8(List<Dish> dishList) {
        return dishList.stream().filter(dish -> dish.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList());
    }

    /**
     * 对数据库查询到的菜肴根据菜肴种类进行分类，返回一个Map<Type,List<Dish>>的结果
     */
    private static Map<Type, List<Dish>> afterJdk8(List<Dish> dishList) {
        return dishList.stream().collect(groupingBy(Dish::getType));
    }

    /**
     * 生成流的方式主要有五种
     */
    private static void generatorStream() throws IOException {
        // 1. 通过集合生成，应用中最常用的一种
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = integerList.stream();
        System.out.println(stream.collect(Collectors.toList()));

        // 2. 通过数组生成
        int[] intArr = new int[]{1, 2, 3, 4, 5};
        // 通过Arrays.stream方法生成流，并且该方法生成的流是数值流【即IntStream】
        // 而不是 Stream<Integer>。补充一点使用数值流可以避免计算过程中拆箱装箱，提高性能
        IntStream stream1 = Arrays.stream(intArr, 0, intArr.length);
        System.out.println(stream1.sum());

        // 3. 通过值生成，通过Stream的of方法生成流，通过Stream的empty方法可以生成一个空流
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        // 4. 通过文件生成，得到的每个流是给定文件中的一行
        // Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset());

        // 5. 通过函数生成提供了 iterator 和 generate 两个静态方法从函数中生成流
        // 第一个为初始化值，第二个为进行的函数操作，因为iterator生成的流为无限流，
        // 通过limit方法对流进行了截断，只生成5个偶数
        Stream<Integer> stream2 = Stream.iterate(0, n -> n + 2).limit(5);
        Stream<Double> stream3 = Stream.generate(Math::random).limit(5);

        // 打印结果为2,3,4,5
        Stream<Integer> skipStream = integerList.stream().skip(2);
    }

    /**
     * 中间操作
     */
    private void middleOperate() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = integerList.stream().distinct();

        Stream<Integer> limit = integerList.stream().limit(3);

        Stream<Integer> skip = integerList.stream().skip(2);

        List<String> stringList = Arrays.asList("Java 8", "Lambds", "In", "Action");
        Stream<Integer> stream1 = stringList.stream().map(String::length);

        List<String> wordList = Arrays.asList("Hello", "World");
        List<String> strList = wordList.stream()
                .map(w -> w.split(" "))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 元素匹配
     */
    private void elementMatch() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        if (integerList.stream().allMatch(i -> i > 3)) {
            System.out.println("值大于3");
        }

        if (integerList.stream().anyMatch(i -> i > 3)) {
            System.out.println("存在大于3的值");
        }
        // 等价于：
        for (Integer i : integerList) {
            if (i > 3) {
                System.out.println("存在大于3的值");
                break;
            }
        }

        if (integerList.stream().noneMatch(i -> i > 3)) {
            System.out.println("值都小于3");
        }
    }

    /**
     * 终端操作
     */
    private void terminalOperate() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);

        long count = integerList.stream().count();
        Long collect = integerList.stream().collect(counting());

        // 通过findFirst方法查找到第一个大于三的元素并打印
        Optional<Integer> first = integerList.stream().filter(i -> i > 3).findFirst();
        // 随机查找一个
        Optional<Integer> any = integerList.stream().filter(i -> i > 3).findAny();

        Integer sum = integerList.stream().reduce(0, (a, b) -> (a + b));
        integerList.stream().reduce(0, Integer::sum);
    }

    private void doCalculation(List<Dish> menu) {
        Optional<Integer> min = menu.stream().map(Dish::getCalories).collect(minBy(Integer::min));
        OptionalInt min1 = menu.stream().mapToInt(Dish::getCalories).min();
        Optional<Integer> min2 = menu.stream().map(Dish::getCalories).collect(minBy(Integer::compareTo));
        Optional<Integer> min3 = menu.stream().map(Dish::getCalories).reduce(Integer::min);

        Optional<Integer> max = menu.stream().map(Dish::getCalories).collect(Collectors.maxBy(Integer::max));
        OptionalInt max1 = menu.stream().mapToInt(Dish::getCalories).max();
        Optional<Integer> max2 = menu.stream().map(Dish::getCalories).collect(maxBy(Integer::compareTo));
        Optional<Integer> max3 = menu.stream().map(Dish::getCalories).reduce(Integer::max);

        int sum1 = menu.stream().collect(summingInt(Dish::getCalories));
        int sum2 = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
        int sum3 = menu.stream().mapToInt(Dish::getCalories).sum();

        double average = menu.stream().collect(averagingInt(Dish::getCalories));

        // 通过summarizingInt同时求总和、平均值、最大值、最小值
        IntSummaryStatistics intSummaryStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        double average1 = intSummaryStatistics.getAverage();
        int intMin = intSummaryStatistics.getMin();
        int intMax = intSummaryStatistics.getMax();
        long sum = intSummaryStatistics.getSum();
    }

    private static void doForeach(List<Dish> menu) {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        integerList.stream().forEach(System.out::println);

        List<String> strings = menu.stream().map(Dish::getName).collect(toList());
        Set<String> sets = menu.stream().map(Dish::getName).collect(toSet());

        String result = menu.stream().map(Dish::getName).collect(joining(","));
        Map<Type, List<Dish>> result1 = menu.stream().collect(groupingBy(Dish::getType));
        Map<Type, Map<Object, List<Dish>>> result2 = menu.stream().collect(groupingBy(Dish::getType,
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                })));

        Map<Boolean, List<Dish>> collect = menu.stream().collect(partitioningBy(Dish::isVegetarian));

        // 返回值的键仍然是布尔类型，但是它的分类是根据范围进行分类的，分区比较适合处理根据范围进行分类
        Map<Boolean, List<Integer>> result4 = integerList.stream().collect(partitioningBy(i -> i > 3));
        System.out.println(result4);
    }
}