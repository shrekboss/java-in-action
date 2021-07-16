package org.crayzer.conc.design.pattern.producerconsumer;

public class Queue {
    private int size = 0, head = 0, tail = 0;
    private Long[] data;

    public Queue(int capacity) {
        this.data = new Long[capacity];
        this.size = capacity;
    }

    public boolean add(Long element) {
        if ((tail + 1) % size == head) return false;
        this.data[tail] = element;
        this.tail = (tail + 1) % size;
        return true;
    }

    public Long poll() {
        if (tail == head) return null;
        Long rtn = this.data[head];
        head = (head + 1) % size;
        return rtn;
    }
}
