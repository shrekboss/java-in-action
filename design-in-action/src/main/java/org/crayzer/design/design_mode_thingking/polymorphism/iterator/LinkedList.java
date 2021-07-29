package org.crayzer.design.design_mode_thingking.polymorphism.iterator;

public class LinkedList implements Iterator {
    private LinkedListNode head;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public String next() {
        return null;
    }

    @Override
    public String remove() {
        return null;
    }

    private class LinkedListNode {
    }
}
