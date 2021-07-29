package org.crayzer.design.design_mode_pattern.behavioural.iterator;

// 接口定义方式一
public interface Iterator<E> {
    boolean hasNext();
    void next();
    E currentItem();
}

// 接口定义方式二
// 返回当前元素与后移一位这两个操作，要放到同一个函数 next() 中完成
// public interface Iterator { boolean hasNext(); E next();}
