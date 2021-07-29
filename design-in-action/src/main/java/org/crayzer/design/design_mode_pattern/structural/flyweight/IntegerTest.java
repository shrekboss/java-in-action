package org.crayzer.design.design_mode_pattern.structural.flyweight;

public class IntegerTest {

    public static void main(String[] args) {

        Integer i1 = 56; // 底层执行了：Integer i = Integer.valueOf(56);
        // int j = i; 底层执行了：int j = i.intValue();
        Integer i2 = 56;
        Integer i3 = 129;
        Integer i4 = 129;
        System.out.println(i1 == i2);
        System.out.println(i3 == i4);

        Integer a = new Integer(123); // 不会使用到 IntegerCache
        Integer b = 123;
        Integer c = Integer.valueOf(123);
        System.out.println(a == b);
        System.out.println(b == c);
    }

    // integer.valueOf 源码
    // 如果要创建的 Integer 对象的值在 -128 到 127 之间，
    // 会从 IntegerCache 类中直接返回，否则才调用 new 方法创建。

    // public static Integer valueOf(int i) {
    //     if (i >= IntegerCache.low && i <= IntegerCache.high)
    //         return IntegerCache.cache[i + (-IntegerCache.low)];
    //     return new Integer(i);
    // }
}
