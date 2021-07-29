package org.crayzer.design.design_mode_thingking.polymorphism.iterator;

public class Array implements Iterator {
    private String[] data;

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
}
