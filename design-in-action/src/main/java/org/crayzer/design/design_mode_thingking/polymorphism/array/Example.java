package org.crayzer.design.design_mode_thingking.polymorphism.array;

public class Example {
    public static void test(DynamicArray dynamicArray) {
        dynamicArray.add(5);
        dynamicArray.add(1);
        dynamicArray.add(3);
        for (int i = 0; i < dynamicArray.size(); ++i) {
            System.out.println(dynamicArray.get(i));
        }
    }

    public static void main(String args[]) {
        DynamicArray dynamicArray = new SortedDynamicArray();
        test(dynamicArray); // 打印结果：1、3、5
    }
}
