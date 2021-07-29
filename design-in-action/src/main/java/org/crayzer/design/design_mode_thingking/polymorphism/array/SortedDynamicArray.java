package org.crayzer.design.design_mode_thingking.polymorphism.array;

public class SortedDynamicArray extends DynamicArray {
    @Override
    public void add(Integer e) {
        ensureCapacity();
        int i;
        for (i = size - 1; i >= 0; --i) {
            if (elements[i] > e) {
                elements[i + 1] = elements[i];
            } else {
                break;
            }
        }
        elements[i + 1] = e;
        ++size;
    }
}
