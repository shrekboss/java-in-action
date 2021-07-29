package org.crayzer.design.design_mode_pattern.behavioural.iterator;

public class Demo {
    public static void main(String[] args) {
        // ArrayList<String> names = new ArrayList<>();
        // names.add("xzg");
        // names.add("wang");
        // names.add("zheng");
        //
        // // Iterator<String> iterator = new ArrayIterator(names);
        // Iterator<String> iterator = names.iterator();
        // while (iterator.hasNext()) {
        //     System.out.println(iterator.currentItem());
        //     iterator.next();
        // }
        //
        // iterator = names.iterator();
        // iterator.next();
        // names.remove("a");
        // iterator.next();//抛出ConcurrentModificationException异常
        testArrayList();
    }


    public static void testArrayList() {
        java.util.List<String> names = new java.util.ArrayList<>();
        names.add("a");
        names.add("b");
        names.add("c");
        names.add("d");
        java.util.Iterator iterator = names.iterator();
        // 不能直接调用集合的remove() 方法
        // while (iterator.hasNext()) {
        //     if (iterator.next() == "b") {
        //         // names.remove("b"); // 报错，抛出 ConcurrentModificationException
        //         iterator.remove();
        //     }
        // }
        System.out.println(names);

        // 它只能删除游标指向的前一个元素，而且一个 next() 函数之后，只能跟着最多一个 remove() 操作，
        // 多次调用 remove() 操作会报错。
        // iterator.next();
        // iterator.remove();
        // iterator.remove(); //报错，抛出IllegalStateException异常

        //
        java.util.Iterator iterator1 = names.iterator();
        java.util.Iterator iterator2 = names.iterator();
        iterator1.next();
        iterator1.remove();
        iterator2.next(); // 运行结果？
    }
}
