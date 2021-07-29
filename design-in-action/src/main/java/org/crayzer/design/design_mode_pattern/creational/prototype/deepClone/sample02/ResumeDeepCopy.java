package org.crayzer.design.design_mode_pattern.creational.prototype.deepClone.sample02;

import org.crayzer.design.design_mode_pattern.creational.prototype.WorkExperience;

import java.util.ArrayList;

public class ResumeDeepCopy implements Cloneable {
    private String name = null;
    private int age = 0;
    private String sex = null;
    private ArrayList<String> famMem = new ArrayList<>();
    private WorkExperience work = null;

    public ResumeDeepCopy(String name) {
        this.name = name;
        this.famMem = famMem;
        work = new WorkExperience();
    }

    /**
     * 重些clone()方法为public类型，为每个字段都创建新的对象，已实现深拷贝功能。
     */
    @Override
    public ResumeDeepCopy clone() throws CloneNotSupportedException {
        int age = this.age;
        String sex = this.sex;
        String name = new String(this.name);
        ArrayList<String> famMem = new ArrayList<>(this.famMem);

        ResumeDeepCopy copy = new ResumeDeepCopy(name);
        copy.setPersonal(sex, age, famMem);
        copy.setWork(this.work.timeArea, this.work.company);
        return copy;
    }// clone

    public void display() {
        System.out.println(this.name + " " + this.sex + " " + this.age);
        System.out.print("Family member: ");
        for (String elem : famMem)
            System.out.print(elem + " ");
        System.out.println();
        System.out.print("Work experience: " + this.work.timeArea);
        System.out.println(" " + this.work.company);
    }// display

    public void setPersonal(String sex, int age, ArrayList<String> famMem) {
        this.age = age;
        this.sex = sex;
        this.famMem = famMem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWork(String timeArea, String company) {
        work.timeArea = timeArea;
        work.company = company;
    }

    public ArrayList<String> getFamMem() {
        return famMem;
    }

    public void setFamMem(ArrayList<String> famMem) {
        this.famMem = famMem;
    }
}
