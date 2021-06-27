package org.crayzer.jvm.bytecode;

public class MovingAverage {
    private int count = 0;
    private double sum;

    public void submit(double value) {
        this.count++;
        this.sum += value;
    }

    public double getAvg() {
        if (0 == this.count) return sum;
        return this.sum / this.count;
    }
}
