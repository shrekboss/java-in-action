package org.crayzer.err.coding.advancedfeatures.genericandinheritance;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 */
public class GenericAndInheritanceApplication {

    public static void main(String[] args) {
        System.out.println("wrong1 方法开始调用");
        wrong1();

        System.out.println("wrong2 方法开始调用");
        wrong2();

        System.out.println("wrong3 方法开始调用");
        wrong3();

        System.out.println("right 方法开始调用");
        right();
    }

    // Child1.setValue called
    // Parent.setValue called
    // Parent.setValue called
    // value: test updateCount: 2

    // getMethods 方法找到了两个名为 setValue 的方法，分别是父类和子类的 setValue 方法
    public static void wrong1() {
        Child1 child1 = new Child1();
        Arrays.stream(child1.getClass().getMethods())
                .filter(method -> "setValue".equals(method.getName()))
                .forEach(method -> {
                    try {
                        method.invoke(child1, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child1.toString());
    }

    // Child1.setValue called
    // Parent.setValue called
    // value: test updateCount: 1

    // 虽然能解决重复记录日志的问题，但没有解决子类方法重写父类方法失败的问题
    public static void wrong2() {
        Child1 child1 = new Child1();
        Arrays.stream(child1.getClass().getDeclaredMethods())
                .filter(method -> "setValue".equals(method.getName()))
                .forEach(method -> {
                    try {
                        method.invoke(child1, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child1.toString());
    }

    // Child2.setValue called
    // Parent.setValue called
    // Child2.setValue called
    // Parent.setValue called
    // value: test updateCount: 2

    // 不通过反射来调用方法，很难发现这个问题。其实，这就是泛型类型擦除导致的问题
    public static void wrong3() {
        Child2 child2 = new Child2();
        Arrays.stream(child2.getClass().getDeclaredMethods())
                .filter(method -> "setValue".equals(method.getName()))
                .forEach(method -> {
                    try {
                        method.invoke(child2, "test");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(child2.toString());
    }

    // Child2.setValue called
    // Parent.setValue called
    // value: test updateCount: 1
    public static void right() {
        Child2 child2 = new Child2();
        Arrays.stream(child2.getClass().getDeclaredMethods())
                // 通过 getDeclaredMethods 方法获取到所有方法后，必须同时根据方法名 setValue 和
                // 非 isBridge 两个条件过滤，才能实现唯一过滤；
                .filter(method -> "setValue".equals(method.getName()) && !method.isBridge())
                // 使用 Stream 时，如果希望只匹配 0 或 1 项的话，可以考虑配合 ifPresent 来使用
                // findFirst 方法。
                .findFirst().ifPresent(method -> {
            try {
                method.invoke(child2, "test");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(child2.toString());
    }
}

class Parent<T> {
    // 用于记录value更新的次数，模拟日志记录的逻辑
    AtomicInteger updateCount = new AtomicInteger();

    private T value;

    @Override
    public String toString() {
        return String.format("value: %s updateCount: %d", value, updateCount.get());
    }

    public void setValue(T value) {
        System.out.println("Parent.setValue called");
        this.value = value;
        updateCount.incrementAndGet();
    }
}

/**
 * 1. 类没有指定 String 泛型参数，父类的泛型方法 setValue(T value) 在泛型擦除后是
 * setValue(Object value)，子类中入参是 String 的 setValue 方法被当作了新方法
 * <p/>
 * 2. 子类的 setValue 方法没有增加 @Override 注解，因此编译器没能检测到重写失败的问题
 */
class Child1 extends Parent {

//     @Override
    public void setValue(String value) {
        System.out.println("Child1.setValue called");
        super.setValue(value);
    }
}

class Child2 extends Parent<String> {

    @Override
    public void setValue(String value) {
        System.out.println("Child2.setValue called");
        super.setValue(value);
    }
}