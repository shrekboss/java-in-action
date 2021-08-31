package org.crayzer.err.coding.numbercalculations.equals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class CommonMistakesApplication {

    public static void main(String[] args) {
        wrong();
        right();
        set();
    }

    private static void wrong() {
        // equals 比较的是 BigDecimal 的 value 和 scale
        System.out.println(new BigDecimal("1.0").equals(new BigDecimal("1")));
    }

    private static void right() {
        System.out.println(new BigDecimal("1.0").compareTo(new BigDecimal("1")) == 0);
    }

    private static void set() {
        // HashSet本质上就是HashMap的key组成的不重复的元素集合，
        // contains方法其实就是根据hashcode和equals去判断相等的
        Set<BigDecimal> hashSet1 = new HashSet<>();
        hashSet1.add(new BigDecimal("1.0"));
        System.out.println(hashSet1.contains(new BigDecimal("1")));//返回false

        Set<BigDecimal> hashSet2 = new HashSet<>();
        hashSet2.add(new BigDecimal("1.0").stripTrailingZeros());
        System.out.println(hashSet2.contains(new BigDecimal("1.000").stripTrailingZeros()));//返回true

        // TreeSet本质TreeMap的key组成的，数据结构是红黑树，是自带排序功能的，可以在放入元素的时
        // 候指定comparator（比较器），或者是放入的元素要实现Comparable接口后元素自己实现
        // compareTo方法，contains方法是根据比较器或者compareTo去判断相等
        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(new BigDecimal("1.0"));
        System.out.println(treeSet.contains(new BigDecimal("1")));//返回true
    }

}

