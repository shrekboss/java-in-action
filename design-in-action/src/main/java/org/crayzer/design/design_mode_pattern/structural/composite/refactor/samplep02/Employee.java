package org.crayzer.design.design_mode_pattern.structural.composite.refactor.samplep02;

public class Employee extends HumanResource {
    public Employee(long id, double salary) {
        super(id);
        this.salary = salary;
    }

    @Override
    public double calculateSalary() {
        return salary;
    }
}
