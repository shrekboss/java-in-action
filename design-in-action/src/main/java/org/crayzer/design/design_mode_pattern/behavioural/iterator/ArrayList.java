package org.crayzer.design.design_mode_pattern.behavioural.iterator;

public class ArrayList<E> implements List<E> {
    protected transient int modCount = 0;
    //...
    @Override
    public Iterator iterator() {
        return new ArrayIterator(this);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public E get(int cursor) {
        return null;
    }

    @Override
    public void add(E e) {

    }

    @Override
    public void remove(E a) {

    }
    //...省略其他代码
}
