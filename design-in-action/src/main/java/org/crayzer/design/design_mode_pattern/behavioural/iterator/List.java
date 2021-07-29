package org.crayzer.design.design_mode_pattern.behavioural.iterator;

public interface List<E> {
    Iterator iterator();
    int size();
    E get(int cursor);
    void add(E e);

    void remove(E a);
    //...省略其他接口函数...
}
