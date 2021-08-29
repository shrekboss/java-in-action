package org.crayzer.err.coding.collection.sublist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class SubListApplication {

    private static List<List<Integer>> data = new ArrayList<>();

    private static void wrong() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = list.subList(1, 4);
        System.out.println(subList);
        // 删除子 List 中的元素影响到了原始 List；
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        try {
            // 尝试为原始 List 增加数字 0 之后再遍历子 List，会出现 ConcurrentModificationException
            subList.forEach(System.out::println);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void right1() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = new ArrayList<>(list.subList(1, 4));
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        subList.forEach(System.out::println);
    }

    private static void right2() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = list.stream().skip(1).limit(3).collect(Collectors.toList());
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);

        subList.forEach(System.out::println);
        System.out.println();
        list.forEach(i -> System.out.print(list.get(i) + " "));
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===============wrong===============");
        // wrong();
        System.out.println("===============right1===============");
        right1();
        System.out.println("===============right2===============");
        right2();
        System.out.println("===============oom===============");
        // oom();
        System.out.println("===============oomfix===============");
        // oomfix();
    }

    /**
     * 出现 OOM 的原因是，循环中的 1000 个具有 10 万个元素的 List 始终得不到回收，因为它始终被
     * subList 方法返回的 List 强引用。
     * <p/>
     * SubList 初始化的时候，并没有把原始 List 中的元素复制到独立的变量中保存，可以认为 SubList 是
     * 原始 List 的视图，并不是独立的 List。双方对元素的修改会相互影响，而且 SubList 强引用了原始的
     * List，所以大量保存这样的 SubList 会导致 OOM
     */
    private static void oom() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(rawList.subList(0, 1));
        }
    }

    private static void oomfix() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(new ArrayList<>(rawList.subList(0, 1)));
        }
    }




}

