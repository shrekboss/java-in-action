package org.crayzer.jvm.opcode;

import java.math.BigDecimal;
import java.util.*;

public class TestGeneric {
    // 传入的参数, 泛型类型最低为 Number
    public static void readOnly(List<? extends Number> list) {
        // 只允许方法返回值的泛型
        Number item = list.get(0);
        System.out.println(item);
        // 因为不知道具体的泛型限制;
        // 所以语法层面不允许传入泛型的方法参数
        // list.add(BigDecimal.ONE);
    }
    // 传入的参数, 泛型类型最高为 Number
    public static void writeOnly(List<? super Number> list) {
        list.add(BigDecimal.ZERO);
        list.add(Integer.valueOf(1));
        // 语法层面不允许使用泛型的返回值; 只可以安全转换为 Object类型
        // 因为不知道具体的泛型限制; 其他类型都可能有问题
        Object item = list.get(1);
        System.out.println(item);
    }
    public static void main(String[] args) {
        List<Long> millisList = Arrays.asList(Long.valueOf(0), System.nanoTime());
        readOnly(millisList);
        // writeOnly(millisList);
        List<Number> numList = new ArrayList<>();
        writeOnly(numList);
        readOnly(numList);
        List<Object> anyList = new ArrayList<>();
        writeOnly(anyList);
        // readOnly(anyList);
    }
}
