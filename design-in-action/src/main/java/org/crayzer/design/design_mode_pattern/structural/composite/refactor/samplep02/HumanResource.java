package org.crayzer.design.design_mode_pattern.structural.composite.refactor.samplep02;


public abstract class HumanResource {
    protected long id;
    protected double salary;

    public HumanResource(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public abstract double calculateSalary();
}


